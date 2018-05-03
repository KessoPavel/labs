#ifndef SCREENMANAGER_H
#define SCREENMANAGER_H


class ScreenManager
{
private:
    int timeTunrOff;
public:
    ScreenManager();
    void setTunrOffTime(int);
    void enableTurnOff();
    void disebleTurnOff();
};

#endif // SCREENMANAGER_H
