#include "OutputPro.h"
#include <string.h>

#include "LOGCTRL.h"
	//#define NO_POS_DEBUG
#include "pos_debug.h"


OutputPro::OutputPro()
{
	
}
OutputPro::~OutputPro()
{
	
}
	//Э��������봦��
int OutputPro::PackageParse(unsigned char *recv, int length)
{
	int i=0;
	cmdID=recv[i++];
	amount = recv[i]*256+recv[i+1];
	return 1;
}
	//Э���¼�����
int OutputPro::Processing()
{
	DBG_PRN(("������ %d g�ĵ���",amount))
	return 1;
}
	//��װ�������ݰ�
int OutputPro::ConstructRspPackage(unsigned char *send, int &length)
{
	int i=0;
	memcpy(send,macNum,10);
	i+=10;
	send[i++]=1;
	length = i;
	return 1;
	
}
