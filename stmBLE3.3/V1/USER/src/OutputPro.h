#ifndef OUTPUT_PRO_H
#define OUTPUT_PRO_H
#include "BaseProtocol.h"

class OutputPro :public BaseProtocol
{
public:
	//Э��������봦��
    int PackageParse(unsigned char *recv, int length);
	//Э���¼�����
	int Processing();
	//��װ�������ݰ�
	int ConstructRspPackage(unsigned char *send, int &length);
	OutputPro();
	~OutputPro();
private:
	int amount;
	int cmdID;

};

#endif
