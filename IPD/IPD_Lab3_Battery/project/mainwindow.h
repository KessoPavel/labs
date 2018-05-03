#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "battery.h"
#include "refresher.h"
#include "screenmanager.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

private:
    Battery * battery;
    Refresher * refresher;
    ScreenManager * screenManager;
    void setValues();
    void closeEvent(QCloseEvent *);
public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
public slots:
    void refresh();
    void checkStatus();
private slots:
    void on_comboBox_currentIndexChanged(int index);
private:
    Ui::MainWindow *ui;


};

#endif // MAINWINDOW_H
