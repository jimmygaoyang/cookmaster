#ifndef EXECUTE_H
#define EXECUTE_H
#include "BLETrans.h"
#include "RS485Trans.h"
#include "OutputPro.h"

class Executor
{
public:
	Executor(char * mac);
	~Executor();
	void processing();

private:
	char macNum[10];
	BLETrans *bleObj;
	RS485Trans *RSObject;
	char *dataBuf;
	int recLen;
};

#endif
