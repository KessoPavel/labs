#-------------------------------------------------
#
# Project created by QtCreator 2017-10-13T12:13:15
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = project
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    battery.cpp \
    refresher.cpp \
    screenmanager.cpp

HEADERS  += mainwindow.h \
    battery.h \
    refresher.h \
    screenmanager.h

FORMS    += mainwindow.ui
