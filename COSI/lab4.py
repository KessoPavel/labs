import numpy as np
from math import *
import time
import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages


#Добавление шумов
def add_noise(data, noise):
    result = [0] * len(data)
    for i in range(len(data)):
        result[i] = data[i] + noise[i]

    return result


#Расчёт импульсной характеристики фильтра
def get_impulse_response(fd, fs, fx, N):
    H = [0] * N  # Импульсная характеристика фильтра
    H_id = [0] * N  # Идеальная импульсная характеристика
    W = [0] * N  # Весовая функция

    fc = (fs + fx) / (2 * fd)

    for i in range(N):
        if i == 0:
            H_id[i] = 2*pi*fc
        else:
            H_id[i] = sin(2*pi*fc*i )/(pi*i)
        #весовая функция Хеминга
        W [i] = 0.54 - 0.46 * cos((2*pi*i) / (N-1))
        H [i] = H_id[i] * W[i]

    return H


#Нормировка импульсной характеристики double
def normalization_impulse_response(H):
    N = len(H)
    result = [0] * N
    SUM = 0

    for i in range(N):
        SUM += H[i]
    for i in range(N):
        result[i] = H[i] / SUM

    return result


#Фильтрация
def filter_data(input, filter):
    out = [0] * len(input)

    for i in range(len(input)):
        out[i]=0.
        for j in range(len(filter) - 1):
            if i >= j:
                out[i]+= filter[j]*input[i-j]

    return out


if __name__ == '__main__':
    N = 20  # Длина фильтра
    Fd = 8000  # Частота дискретизации входных данных
    Fs = 100 # Частота полосы пропускания
    Fx = 1 / (4 * pi)  # Частота полосы затухания

    # initialization
    input = [None] * 64
    noise = [None] * 64
    t = [0] * 64
    step = 1 / (4 * pi)

    for i in range(64):
        input[i] = cos(3*t[i]) + sin(2*t[i])
        noise[i] = sin(10 * t[i])
        if i < 63:
            t[i + 1] = t[i] + step

    input_wint_noise = add_noise(input, noise)

    pp = PdfPages('report_lab_4.pdf')
    # input
    plt.plot(t, input)
    plt.xlabel('t')
    plt.ylabel('cos(x*3) + sin(x*2)')
    plt.title('input signal')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    # noise
    plt.plot(t, input_wint_noise)
    plt.xlabel('t')
    plt.ylabel('cos(x*3) + sin(x*2)')
    plt.title('input signal with noise')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    imp_resp = get_impulse_response(Fd, Fs, Fx, N)
    filter = normalization_impulse_response(imp_resp)

    result = filter_data(input_wint_noise, filter)

    # result
    plt.plot(t, result)
    plt.xlabel('t')
    plt.ylabel('cos(x*3) + sin(x*2)')
    plt.title('after filter')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    pp.close()