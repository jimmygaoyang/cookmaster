#include "GlobalIOSet.h"


CIOObject::CIOObject(GPIO_TypeDef * IOGroup, unsigned short IOnum, unsigned int IOrcc)
{
	IO_Group = IOGroup;
	IO_num = IOnum;
	IO_rcc = IOrcc;
}

void CIOObject::SetMode(int director)
{
	GPIO_InitTypeDef GPIO_InitStructure;
	RCC_APB2PeriphClockCmd(IO_rcc,ENABLE);
	GPIO_InitStructure.GPIO_Pin = IO_num;
	if(director == IN)
	{
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;	
	}
	if(director == OUT)
	{
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_Out_PP;
	}
	if(director == ANALOG)
	{
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AIN;
	}
	GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_Init(IO_Group, &GPIO_InitStructure);	
}

void CIOObject::SetDigitalOut(int Status)
{
	if(Status == HIGH)
	{
		GPIO_SetBits(IO_Group,IO_num);
	}
	if(Status == LOW)
	{
		GPIO_ResetBits(IO_Group,IO_num);
	}	
}

int CIOObject::ReadDigitalIn()
{
	if(GPIO_ReadInputDataBit(IO_Group,IO_num)==Bit_RESET)
	{
		return LOW;	
	}
	else
	{
		return HIGH;
	}
}



CGlobalIOSet::CGlobalIOSet()
{
	m_IN_BLEState = new CIOObject(GPIOC,GPIO_Pin_6,RCC_APB2Periph_GPIOC);
	m_IN_BLEState->SetMode(IN);
	m_OUT_BLEEnable =  new CIOObject(GPIOC,GPIO_Pin_7,RCC_APB2Periph_GPIOC);
	m_OUT_BLEEnable->SetMode(OUT); 
	m_OUT_485Direct = new CIOObject(GPIOC,GPIO_Pin_12,RCC_APB2Periph_GPIOC);
	m_OUT_485Direct->SetMode(OUT);
}

