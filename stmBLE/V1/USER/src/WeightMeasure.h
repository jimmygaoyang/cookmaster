/***********************************************************************
 * Module:  WeightMeasure.h
 * Author:  Thinkpad
 * Modified: 2015-07-20 15:41:08
 * Purpose: Declaration of the class WeightMeasure
 ***********************************************************************/

#if !defined(__cmstm_WeightMeasure_h)
#define __cmstm_WeightMeasure_h
#include "GlobalIOSet.h"

class WeightMeasure
{
public:
	WeightMeasure();
   double GetWeight(void);
   // return difference votage, will be blocked if hx711 is not ready
   long read();

protected:
private:
   // set amplification factor, take effect after one call to read()
   void set_amp(char amp);
   // test hx711 is ready or not, will be called in read()
    bool is_ready();
   // return (read() - offset) * coefficient
	double bias_read();
    // set no-load value to offset, euqla to average of t times read();
    void tare(int t = 10);
    // set coefficient
    void set_co(double co = 1);
    // set offset
    void set_offset(long offset = 0);
private:
   CGlobalIOSet *g_IOset;
    char AMP;
    long OFFSET;
    double COEFFICIENT;

};

#endif