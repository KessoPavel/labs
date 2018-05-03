#include "screenmanager.h"
#include <QString>
#include <stdio.h>

ScreenManager::ScreenManager()
{
    this->timeTunrOff = 0;
}

void ScreenManager::setTunrOffTime(int newTime)
{
    this->timeTunrOff = newTime;
}

void ScreenManager::enableTurnOff()
{
    QString comand = "xset dpms ";
    comand += QString::number(this->timeTunrOff);
    comand += " 0 0";

    system(comand.toStdString().c_str());
}

void ScreenManager::disebleTurnOff()
{
    system("xset dpms 0 0 0");
}

