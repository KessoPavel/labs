#include "refresher.h"
#include <unistd.h>
Refresher::Refresher(QObject *parent) : QObject(parent)
{

}

void Refresher::doWork(){
    while(true){
        emit this->refresh();
        usleep(1000000);
    }
}
