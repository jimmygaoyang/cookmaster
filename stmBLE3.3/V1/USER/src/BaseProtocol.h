#ifndef BASE_PROTOCOL_H
#define BASE_PROTOCOL_H

class BaseProtocol
{
public:
//Э��������봦��
    virtual int PackageParse(unsigned char *recv, int length) = 0;
//Э���¼�����
	virtual int Processing() = 0;
	//��װ�������ݰ�
	virtual int ConstructRspPackage(unsigned char *send, int &length) = 0;
	void setMac( char *mac);
	BaseProtocol();
	~BaseProtocol();
public:
	char macNum[10];
};

#endif



