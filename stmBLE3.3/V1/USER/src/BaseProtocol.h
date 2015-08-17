#ifndef BASE_PROTOCOL_H
#define BASE_PROTOCOL_H

class BaseProtocol
{
public:
//协议包解析与处理
    virtual int PackageParse(unsigned char *recv, int length) = 0;
//协议事件处理
	virtual int Processing() = 0;
	//组装返回数据包
	virtual int ConstructRspPackage(unsigned char *send, int &length) = 0;
	void setMac( char *mac);
	BaseProtocol();
	~BaseProtocol();
public:
	char macNum[10];
};

#endif



