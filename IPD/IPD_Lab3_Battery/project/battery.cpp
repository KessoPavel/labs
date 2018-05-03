#include "battery.h"
#include <QFile>
#include <QByteArray>
#include <QString>

Battery::Battery()
{

    this->readStatus();

    QFile * ueventFile = new QFile("/sys/class/power_supply/BAT1/uevent");
    if(!ueventFile->open(QIODevice::ReadOnly)){
        return;
    }

    QString temp;
    int energy_full_design;
    while(!ueventFile->NoError) {
        temp = ueventFile->readLine();
        if(temp.startsWith("POWER_SUPPLY_POWER_NOW")){
            temp = temp.remove("POWER_SUPPLY_POWER_NOW=");
            this->power_now = temp.toInt();
            continue;
        }
        if(temp.startsWith("POWER_SUPPLY_ENERGY_FULL_DESIGN")){
            temp = temp.remove("POWER_SUPPLY_ENERGY_FULL_DESIGN=");
            energy_full_design = temp.toInt();
            continue;
        }
        if(temp.startsWith("POWER_SUPPLY_ENERGY_FULL")){
            temp = temp.remove("POWER_SUPPLY_ENERGY_FULL=");
            this->energy_full = temp.toInt();
            continue;
        }
        if(temp.startsWith("POWER_SUPPLY_ENERGY_NOW")){
            temp = temp.remove("POWER_SUPPLY_ENERGY_NOW=");
            this->energy_now = temp.toInt();
            continue;
        }
        if(temp.startsWith("POWER_SUPPLY_CAPACITY")){
            temp = temp.remove("POWER_SUPPLY_CAPACITY=");
            this->charge = temp.toInt();
            break;
        }
    }


    this->wear = ((energy_full_design - this->energy_full)*100)/energy_full_design;

    if(this->status == "AC")
        this->time = ((double)this->energy_full - this->energy_now)/this->power_now;
    else
        this->time = (double)this->energy_now / this->power_now;
}

void Battery::readStatus(){
    QFile * statusFile = new QFile("/sys/class/power_supply/BAT1/status");
    if(!statusFile->open(QIODevice::ReadOnly)){
        return;
    }

    QString temp = statusFile->readAll();
    if(temp.startsWith("Discharging"))
        this->status = "Battery";
    else
        this->status = "AC";

    statusFile->close();
}

void Battery::readInfo(){
    QFile * ueventFile = new QFile("/sys/class/power_supply/BAT1/uevent");
    if(!ueventFile->open(QIODevice::ReadOnly)){
        return;
    }

    while(!ueventFile->NoError) {
        QString temp = ueventFile->readLine(50);
        if(temp.startsWith("POWER_SUPPLY_POWER_NOW")){
            temp = temp.remove("POWER_SUPPLY_POWER_NOW=");
            this->power_now = temp.toInt();
            continue;
        }
        if(temp.startsWith("POWER_SUPPLY_ENERGY_FULL")){
            temp = temp.remove("POWER_SUPPLY_ENERGY_FULL=");
            this->energy_full = temp.toInt();
            continue;
        }
        if(temp.startsWith("POWER_SUPPLY_ENERGY_NOW")){
            temp = temp.remove("POWER_SUPPLY_ENERGY_NOW=");
            this->energy_now = temp.toInt();
            continue;
        }
        if(temp.startsWith("POWER_SUPPLY_CAPACITY")){
            temp = temp.remove("POWER_SUPPLY_CAPACITY=");
            this->charge = temp.toInt();
            break;
        }
    }
    if(this->status == "AC")
        this->time = ((double)this->energy_full - this->energy_now)/this->power_now;
    else
        this->time = (double)this->energy_now / this->power_now;
}

void Battery::refresh(){
    this->readStatus();
    this->readInfo();
}

int Battery::getCharge(){
    return this->charge;
}

QString Battery::getStatus(){
    return this->status;
}

int Battery::getWear(){
    return this->wear;
}

QString Battery::getTime(){
    int h = (int)(this->time);
    int m = (int)((this->time-(int)this->time)*60);

    QString answer = "";
    if(h != 0)
        answer+=QString::number(h)+" h. ";
    answer+=QString::number(m)+" min.";

    if(this->status == "AC")
        answer+=" until full";
    else
        answer+=" until empty";
    return  answer;
}

