#ifndef OUTPUT_PRO_H
#define OUTPUT_PRO_H
#include "BaseProtocol.h"

class OutputPro :public BaseProtocol
{
public:
	//协议包解析与处理
    int PackageParse(unsigned char *recv, int length);
	//协议事件处理
	int Processing();
	//组装返回数据包
	int ConstructRspPackage(unsigned char *send, int &length);
	OutputPro();
	~OutputPro();
private:
	int amount;
	int cmdID;

};

#endif
