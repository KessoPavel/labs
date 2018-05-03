#ifndef REFRESHER_H
#define REFRESHER_H

#include <QObject>

class Refresher : public QObject
{
    Q_OBJECT
public:
    explicit Refresher(QObject *parent = 0);
signals:
    void refresh();
public slots:
    void doWork();
};

#endif // REFRESHER_H
