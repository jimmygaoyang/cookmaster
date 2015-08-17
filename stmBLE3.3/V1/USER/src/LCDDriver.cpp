/***********************************************************************
 * Module:  LCDDriver.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-20 15:28:48
 * Purpose: Implementation of the class LCDDriver
 ***********************************************************************/

#include "LCDDriver.h"
#include "BuffType.h"
#include <string.h>
#include "DelayFun.h"


LCDDriver::LCDDriver()
{
	g_IOSet = CSingleton<CGlobalIOSet>::instance();
	LCD_write(0x38,0);
	Delay_ms(5);
	LCD_write(0x38,0);
	Delay_ms(5);
	LCD_write(0x38,0);
	LCD_write(0x10,0);
	LCD_write(0x08,0);
	LCD_write(0x01,0);
	LCD_write(0x06,0);
	LCD_write(0x0C,0); 
	LCD_write(0x80,0);
	LCD_write('A',1);
}
////////////////////////////////////////////////////////////////////////
// Name:       LCDDriver::LCD_write(int buf)
// Purpose:    Implementation of LCDDriver::LCD_write()
// Parameters:
// - data 
// - rs  rs =1 cmd  rs=0 data
// Return:     void
////////////////////////////////////////////////////////////////////////
void LCDDriver::LCD_write(char data, int rs)
{
	if(rs == 1)
		g_IOSet->m_OUT_LCD_RS->SetDigitalOut(HIGH);//Êý¾ÝÃüÁî
	else
		g_IOSet->m_OUT_LCD_RS->SetDigitalOut(LOW);//¿ØÖÆÃüÁî

	complex8_t tmp;
	tmp.all=data;
	g_IOSet->m_OUT_LCD_DB0->SetDigitalOut(tmp.bit.BIT_0);
	g_IOSet->m_OUT_LCD_DB1->SetDigitalOut(tmp.bit.BIT_1);
	g_IOSet->m_OUT_LCD_DB2->SetDigitalOut(tmp.bit.BIT_2);
	g_IOSet->m_OUT_LCD_DB3->SetDigitalOut(tmp.bit.BIT_3);
	g_IOSet->m_OUT_LCD_DB4->SetDigitalOut(tmp.bit.BIT_4);
	g_IOSet->m_OUT_LCD_DB5->SetDigitalOut(tmp.bit.BIT_5);
	g_IOSet->m_OUT_LCD_DB6->SetDigitalOut(tmp.bit.BIT_6);
	g_IOSet->m_OUT_LCD_DB7->SetDigitalOut(tmp.bit.BIT_7);

	Delay_us(2);

	g_IOSet->m_OUT_LCD_EN->SetDigitalOut(HIGH);
	Delay_us(1);
	g_IOSet->m_OUT_LCD_EN->SetDigitalOut(LOW);
	Delay_us(1);
	
	Delay_ms(1);
	
	
	
	
	
	
	
}

////////////////////////////////////////////////////////////////////////
// Name:       LCDDriver::SetFrame(int buf)
// Purpose:    Implementation of LCDDriver::SetFrame()
// Parameters:
// - buf
// Return:     void
////////////////////////////////////////////////////////////////////////

void LCDDriver::SetFrame(char *buf)
{
   // TODO : implement
   memcpy(frame,buf,32);
}

////////////////////////////////////////////////////////////////////////
// Name:       LCDDriver::GetFrame()
// Purpose:    Implementation of LCDDriver::GetFrame()
// Return:     int
////////////////////////////////////////////////////////////////////////

void LCDDriver::GetFrame(char *buf)
{
   // TODO : implement
   memcpy(buf,frame,32);
}

////////////////////////////////////////////////////////////////////////
// Name:       LCDDriver::Show()
// Purpose:    Implementation of LCDDriver::Show()
// Return:     int
////////////////////////////////////////////////////////////////////////

int LCDDriver::Show(void)
{
   // TODO : implement
   int i=0;
   LCD_write(0x80,0);
   for(i=0;i<16;i++)
   {
   	LCD_write(frame[i],1);
		Delay_ms(1);
   }

   LCD_write(0xC0,0);
   for(i=0;i<16;i++)
   {
   	LCD_write(frame[i+16],1);
		Delay_ms(1);
   }
}

////////////////////////////////////////////////////////////////////////
// Name:       LCDDriver::ShowShining()
// Purpose:    Implementation of LCDDriver::ShowShining()
// Return:     int
////////////////////////////////////////////////////////////////////////

int LCDDriver::ShowShining(int pos)
{
   // TODO : implement
   char dat = frame[pos];
   
   frame[pos] = ' ';
   Show();
   delay_ms(500);
   frame[pos] = dat;
   Show();
   delay_ms(500);
   
   
}