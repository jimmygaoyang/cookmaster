#include "E2PROMDriver.h"
#include "stm32f10x.h"


#define I2C_Speed_I1		200000		//外设频率

#define I2C1_DR_Address		0x40005410	//I2C1数据寄存器的地址

#define Transmitter			0x00
#define Receiver			0x01



//*************************************************//
// 函数名  : RCC_Configuration					   //
// 功能    : 时能时钟						   	   //
// Input   : None								   //
// Output  : None								   //
// Return  : None								   //
//*************************************************//
void RCC_Configuration_I2C(void)
{
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB | RCC_APB2Periph_AFIO, ENABLE);
	RCC_APB1PeriphClockCmd(RCC_APB1Periph_I2C1, ENABLE);
}

//*************************************************//
// 函数名  : GPIO_Configuration					   //
// 功能    : 配置I/O端口						   //
// Input   : None								   //
// Output  : None								   //
// Return  : None								   //
//*************************************************//
void GPIO_Configuration_I2C(void)
{
	GPIO_InitTypeDef  GPIO_InitStructure; 
	/* Configure I2C1 pins: SCL（PB6） and SDA（PB7） */
	GPIO_InitStructure.GPIO_Pin =  GPIO_Pin_6 | GPIO_Pin_7;
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_OD;
	GPIO_Init(GPIOB, &GPIO_InitStructure);
}
//*************************************************//
// 函数名  : I2C_Configuration					   //
// 功能    : 配置I2C				     		   //
// Input   : None								   //
// Output  : None								   //
// Return  : None								   //
//*************************************************//
void I2C_Configuration(void)
{
	I2C_InitTypeDef  I2C_InitStructure; 
	
	/* I2C configuration */
	I2C_InitStructure.I2C_Mode = I2C_Mode_I2C;
	I2C_InitStructure.I2C_DutyCycle = I2C_DutyCycle_2;
	I2C_InitStructure.I2C_OwnAddress1 = EEPROM_ADDRESS;
	I2C_InitStructure.I2C_Ack = I2C_Ack_Enable;
	I2C_InitStructure.I2C_AcknowledgedAddress = I2C_AcknowledgedAddress_7bit;	
	I2C_InitStructure.I2C_ClockSpeed = I2C_Speed_I1;
	/* I2C Peripheral Enable */
	I2C_Cmd(I2C1, ENABLE);
	/* Apply I2C configuration after enabling it */
	I2C_Init(I2C1, &I2C_InitStructure);

}



E2PROMDriver::E2PROMDriver()
{
	//配置时钟
	RCC_Configuration_I2C();
	//配置IO
	GPIO_Configuration_I2C();
	//配置I2C
	I2C_Configuration();

}


void E2PROMDriver::MultiByteWrite(unsigned char* pBuffer, unsigned char WriteAddr, unsigned char NumByteToWrite)
{
	u8 i = 0;

	if(NumByteToWrite > I2C_PageSize)
		NumByteToWrite = I2C_PageSize;	

	/* Send STRAT condition */
	I2C_GenerateSTART(I2C1, ENABLE);
	
	/* Test on EV5 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_MODE_SELECT));  
	
	/* Send EEPROM address for write */
	I2C_Send7bitAddress(I2C1, EEPROM_ADDRESS, I2C_Direction_Transmitter);
	
	/* Test on EV6 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED));
	
	I2C_SendData(I2C1, (u8)WriteAddr);
	
	/* Test on EV8 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_TRANSMITTED));
	
	
	for (i = 0; i < NumByteToWrite; i++,pBuffer++)
	{	
		I2C_SendData(I2C1, *pBuffer);
		/* Test on EV8 and clear it */
		while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_TRANSMITTED));	
	}
	/* Send STOP condition */
	I2C_GenerateSTOP(I2C1, ENABLE);


}

void E2PROMDriver::MultiByteRead(unsigned char* pBuffer, unsigned char ReadAddr, unsigned short NumByteToRead)
{
	u8 i = 0;

	if(NumByteToRead > I2C_PageSize)
		NumByteToRead = I2C_PageSize;	

	/* Send STRAT condition */
	I2C_GenerateSTART(I2C1, ENABLE);
	
	/* Test on EV5 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_MODE_SELECT));  
	
	/* Send EEPROM address for write */
	I2C_Send7bitAddress(I2C1, EEPROM_ADDRESS, I2C_Direction_Transmitter);
	
	/* Test on EV6 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_TRANSMITTER_MODE_SELECTED));
	
	I2C_SendData(I2C1, (u8)ReadAddr);
	
	/* Test on EV8 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_TRANSMITTED));
	
	/* Send STOP condition */
	I2C_GenerateSTOP(I2C1, ENABLE);
	
	
	/* Send STRAT condition */
	I2C_GenerateSTART(I2C1, ENABLE);
	
	/* Test on EV5 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_MODE_SELECT)); 
	
	/* Send EEPROM address for read */
	I2C_Send7bitAddress(I2C1, EEPROM_ADDRESS, I2C_Direction_Receiver);
	
	
	/* Test on EV6 and clear it */
	while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_RECEIVER_MODE_SELECTED));
	
	for (i = NumByteToRead; i > 0; i--,pBuffer++)
	{
		if (i == 1) 
		{
			I2C_AcknowledgeConfig(I2C1, DISABLE);
			while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_RECEIVED));  
			*pBuffer = I2C_ReceiveData(I2C1);
			I2C_AcknowledgeConfig(I2C1, ENABLE);
	
		}
		else 
		{
			I2C_AcknowledgeConfig(I2C1, ENABLE);
			while(!I2C_CheckEvent(I2C1, I2C_EVENT_MASTER_BYTE_RECEIVED));  
			*pBuffer = I2C_ReceiveData(I2C1);  
			i=i;
		}
	}
	
	/* Send STOP condition */
	I2C_GenerateSTOP(I2C1, ENABLE);

}





