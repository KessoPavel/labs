#ifndef BATTERY_H
#define BATTERY_H
#include <QString>
/*
energy_full (емкость батареи на данный момент);
energy_full_design (первоначальная емкость аккумулятора).
Power Supply Capacity: неизрасходованная емкость аккумулятора в процентах.
status (Charging - сеть, Discharging - батарея)*/
class Battery
{
private:
    QString status;     //текущий статус  / файл status
    double wear;        //износ батареи   / uevent -> POWER_SUPPLY_ENERGY_FULL_DESIGN  && uevent -> POWER_SUPPLY_ENERGY_FULL
    int charge;         //заряд в %       / uevent -> POWER_SUPPLY_CAPACITY
    double time;           //оставшееся время работы

    int energy_full;
    int energy_now;
    int power_now;

    void readStatus();
    void readInfo();
public:
    Battery();
    int getCharge();
    QString getStatus();
    int getWear();
    QString getTime();
    void refresh();
};

#endif // BATTERY_H
