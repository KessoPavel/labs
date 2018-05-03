#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "battery.h"
#include <QString>
#include <QThread>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{

    this->setWindowTitle("Battery Manager");
    QThread *thread= new QThread;
    refresher = new Refresher();
    refresher->moveToThread(thread);

    battery = new Battery();

    screenManager = new ScreenManager();

    ui->setupUi(this);
    this->setValues();

    connect(this->refresher,SIGNAL(refresh()),this,SLOT(refresh()));
    connect(thread, SIGNAL(started()), this->refresher, SLOT(doWork()));

    thread->start();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::setValues(){
    ui->percent->setText(QString::number(battery->getCharge()) + "%");
    ui->progressBar->setValue(battery->getCharge());

    ui->connection->setText(battery->getStatus());

    ui->wear->setText(QString::number(battery->getWear()) + "%");

    ui->time->setText(battery->getTime());
}

void MainWindow::refresh(){
    ui->comboBox->setEnabled(true);
    this->battery->refresh();
    this->setValues();

    this->checkStatus();
}

void MainWindow::checkStatus(){
    if(battery->getStatus() == "AC") {
        screenManager->disebleTurnOff();
        ui->comboBox->setCurrentIndex(0);
        ui->comboBox->setEnabled(false);
    }
}

void MainWindow::on_comboBox_currentIndexChanged(int index)
{

    if(index < 7)
        if(index == 1)
            screenManager->setTunrOffTime(5);
        else
            screenManager->setTunrOffTime(index * 60);
    else
        screenManager->setTunrOffTime(600);
    screenManager->enableTurnOff();
}

void MainWindow::closeEvent(QCloseEvent *){
    screenManager->disebleTurnOff();
}
