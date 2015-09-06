/***********************************************************************
 * Module:  WeightMeasure.cpp
 * Author:  Thinkpad
 * Modified: 2015-07-20 15:41:08
 * Purpose: Implementation of the class WeightMeasure
 ***********************************************************************/

#include "WeightMeasure.h"
#include "DelayFun.h"


WeightMeasure::WeightMeasure()
{
	g_IOset = CSingleton<CGlobalIOSet>::instance();
	set_amp(128);
    COEFFICIENT = 1;
	
	g_IOset->m_OUT_HX711_SCK->SetDigitalOut(LOW);
	set_co(0.0006135);
	set_offset(8021000);
	
	read();
	Delay_ms(400);
	
}
////////////////////////////////////////////////////////////////////////
// Name:       WeightMeasure::GetWeight()
// Purpose:    Implementation of WeightMeasure::GetWeight()
// Return:     int
////////////////////////////////////////////////////////////////////////

double WeightMeasure::GetWeight(void)
{
   // TODO : implement
	double sum=0;
	for(int i=0;i<5;i++)
		sum+=bias_read();
	double average = (double)sum/(double)5.0;
   return average;
}

void WeightMeasure::set_amp(char amp) {
    switch (amp) {
        case 32: AMP = 2; break;
        case 64: AMP = 3; break;
        case 128: AMP = 1; break;
    }
}

bool WeightMeasure::is_ready() {
	if(g_IOset->m_IN_HX711_DA->ReadDigitalIn() == LOW)

	return true;
	else 
		return false;
	
}

long WeightMeasure::read() {
    long val = 0;
//	int timeout = 1000;
//	g_IOset->m_IN_HX711_DA->SetDigitalOut(HIGH);
	g_IOset->m_OUT_HX711_SCK->SetDigitalOut(LOW);
	do{
			;
	}while(!is_ready());
//    while (!is_ready())
//	{
//		timeout--;
//		Delay_ms(10);
//		if(timeout<0)
//			return 0;
//		;
//	}
    for (int i = 0; i < 24; i++) {
        g_IOset->m_OUT_HX711_SCK->SetDigitalOut(HIGH);
			Delay_us(1);
		g_IOset->m_OUT_HX711_SCK->SetDigitalOut(LOW);
			Delay_us(1);
        val <<= 1;
        if (g_IOset->m_IN_HX711_DA->ReadDigitalIn() == HIGH) val++;
    }
    for (int i = 0; i < AMP; i++) {
        g_IOset->m_OUT_HX711_SCK->SetDigitalOut(HIGH);
			Delay_us(1);
		g_IOset->m_OUT_HX711_SCK->SetDigitalOut(LOW);
			Delay_us(1);
    }
	val = val^0x800000;
    //return val & (1L << 23) ? val | ((-1L) << 24) : val;
    return val;
}


double WeightMeasure::bias_read() {
    return (read() - OFFSET) * COEFFICIENT;
}

void WeightMeasure::tare(int t) {
    double sum = 0;
    for (int i = 0; i < t; i++) {
        sum += read();
    }
    set_offset(sum / t);
}

void WeightMeasure::set_offset(long offset) {
    OFFSET = offset;
}

void WeightMeasure::set_co(double co) {
    COEFFICIENT = co;
}
