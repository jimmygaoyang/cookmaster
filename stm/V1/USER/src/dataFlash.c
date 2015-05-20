#include "DataFlash.h"

unsigned short Flash_Write_Without_check(unsigned int iAddress, unsigned char *buf, unsigned short iNumByteToWrite) 
{
    unsigned short i;
    volatile FLASH_Status FLASHStatus = FLASH_COMPLETE;
    i = 0;
    
//    FLASH_UnlockBank1();
    while((i < iNumByteToWrite) && (FLASHStatus == FLASH_COMPLETE))
    {
      FLASHStatus = FLASH_ProgramHalfWord(iAddress, *(unsigned short*)buf);
      i = i+2;
      iAddress = iAddress + 2;
      buf = buf + 2;
    }
    
    return iNumByteToWrite;
}
/**
  * @brief  Programs a half word at a specified Option Byte Data address.
  * @note   This function can be used for all STM32F10x devices.
  * @param  Address: specifies the address to be programmed.
  * @param  buf: specifies the data to be programmed.
  * @param  iNbrToWrite: the number to write into flash
  * @retval if success return the number to write, -1 if error
  *  
  */
int Flash_Write(unsigned int iAddress, unsigned char *buf, unsigned int iNbrToWrite) 
{
       /* Unlock the Flash Bank1 Program Erase controller */
	unsigned int secpos;
	unsigned int iNumByteToWrite = iNbrToWrite;
	unsigned short secoff;
	unsigned short secremain;  
	unsigned short i = 0;    
	unsigned char tmp[FLASH_PAGE_SIZE];
	volatile FLASH_Status FLASHStatus = FLASH_COMPLETE;
        
       FLASH_UnlockBank1();
	secpos=iAddress & (~(FLASH_PAGE_SIZE -1 )) ;//������ַ 
	secoff=iAddress & (FLASH_PAGE_SIZE -1);     //�������ڵ�ƫ��
	secremain=FLASH_PAGE_SIZE-secoff;           //����ʣ��ռ��С 
        
        
	if(iNumByteToWrite<=secremain) secremain = iNumByteToWrite;//������4096���ֽ�

	while( 1 ) 
	{
		Flash_Read(secpos, tmp, FLASH_PAGE_SIZE);   //������������
             for(i=0;i<secremain;i++)
		{       //У������
			if(tmp[secoff+i]!=0XFF)
				break;       //��Ҫ���� 
		}
             if(i<secremain) 
		{//��Ҫ����
			FLASHStatus = FLASH_ErasePage(secpos); //�����������
			if(FLASHStatus != FLASH_COMPLETE)
				return -1;
			for(i=0;i<secremain;i++) 
			{   //����
				tmp[i+secoff]=buf[i];  
		 	}
                	Flash_Write_Without_check(secpos ,tmp ,FLASH_PAGE_SIZE);//д����������  
            } else
            {
			Flash_Write_Without_check(iAddress,buf,secremain);//д�Ѿ������˵�,ֱ��д������ʣ������.
            }
            
            if(iNumByteToWrite==secremain) //д�������
                break;
            else 
	     {
			secpos += FLASH_PAGE_SIZE;
			secoff = 0;//ƫ��λ��Ϊ0 
			buf += secremain;  //ָ��ƫ��
			iAddress += secremain;//д��ַƫ��   
			iNumByteToWrite -= secremain; //�ֽ����ݼ�
			if(iNumByteToWrite>FLASH_PAGE_SIZE) secremain=FLASH_PAGE_SIZE;//��һ����������д����
			else secremain = iNumByteToWrite; //��һ����������д����
            }
            
	}
        
        FLASH_LockBank1();
        return iNbrToWrite; 
}






/**
  * @brief  Programs a half word at a specified Option Byte Data address.
  * @note   This function can be used for all STM32F10x devices.
  * @param  Address: specifies the address to be programmed.
  * @param  buf: specifies the data to be programmed.
  * @param  iNbrToWrite: the number to read from flash
  * @retval if success return the number to write, without error
  *  
  */
int Flash_Read(unsigned int iAddress, unsigned char *buf, int iNbrToRead) {
        int i = 0;
        while(i < iNbrToRead ) {
           *(buf + i) = *(__IO unsigned char *) iAddress++;
           i++;
        }
        return i;
} 
