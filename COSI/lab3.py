import numpy as nm
from cmath import *
import math
import time
import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages

r = [[1, 1, 1, 1, -1, -1, -1, -1],
     [1, 1, -1, -1, 1, 1, -1, -1],
     [1, -1, 1, -1, 1, -1, 1, -1]]


def create_current_matrix(matrix):
    l = len(matrix)
    result = nm.empty([l * 2, l * 2], dtype=int)

    for r in range(l):
        for c in range(l):
            result[r][c] = result[r + l][c] = result[r][c + l] = matrix[r][c]
            result[r + l][c + l] = - matrix[r][c]

    return result


def get_matrix(coef):
    if coef == 0:
        return [[1]]
    else:
        prev_matr = get_matrix(coef - 1)
        return create_current_matrix(prev_matr)


def int_to_bin(n):
    s = str(bin(n))
    b = [0] * 3
    i = 2
    for c in s[::-1]:
        if c == 'b':
            break;
        elif int(c) == 1:
            b[i] = 1
            i -= 1
        else:
            i -= 1

    return b


def get_wal_fans():
    wal = [None] * 8
    for i in range(8):
        n = int_to_bin(i)
        r_c = [n[2] ^ n[1], n[1] ^ n[0], n[0] ^ 0]

        wal[i] = [1, 1, 1, 1, 1, 1, 1, 1]

        for j in range(3):
            if r_c[j] == 0:
                continue
            else:
                for k in range(8):
                    wal[i][k] *= r[j][k]

    return wal


def ifut(signal):
    def fun(a):
        l = len(a)
        if l == 1:
            return a
        else:
            up = fun(a[0:int(l / 2)])
            down = fun(a[int(l / 2):l])
            return list(map(lambda x, y: x + y, up, down)) + list(map(lambda x, y: x - y, up, down))

    return fun(signal)


def fut(signal):
    def fun(a):
        l = len(a)
        if l == 1:
            return a
        else:
            up = fun(a[0:int(l / 2)])
            down = fun(a[int(l / 2):l])
            return list(map(lambda x, y: x + y, up, down)) + list(map(lambda x, y: x - y, up, down))

    return list(map(lambda x: x / 8, fun(signal)))


def dut_Walsh(signal):
    return list(map(lambda x: x / 8, nm.array(get_wal_fans()).__matmul__(nm.array(signal).transpose())))


def idut_Walsh(signal):
    return nm.array(get_wal_fans()).transpose().__matmul__(nm.array(signal).transpose())


def dut(signal):
    return list(map(lambda x: x / 8, get_matrix(3).__matmul__(nm.array(signal).transpose())))


def idut(signal):
    return get_matrix(3).transpose().__matmul__(nm.array(signal).transpose())



if __name__ == '__main__':
    wal = get_wal_fans()
    print('Функции Уолша, упорядоченные по частоте')
    for w in wal:

        s = ''
        for i in range(len(w)):
            if w[i] == 1:
                s += '+'
            else:
                s += '-'
        print(s)

    print('Функции Уолша, упорядоченные по Адамару')
    matr = get_matrix(3)
    for m in matr:
        s = ''
        for c in m:
            if c == 1:
                s += '+'
            else:
                s += '-'
        print(s)

    N = 8
    input_signal0 = [None] * N
    t = [0] * N
    step = 1 / pi

    for i in range(N):
        input_signal0[i] = math.cos(3 * t[i]) + math.sin(2 * t[i])
        if i < N - 1:
            t[i + 1] = t[i] + step



    # print(fut(input_signal0))
    # print(dut(input_signal0))
    # print(input_signal0)
    # print(idut(dut(input_signal0)))


    pp = PdfPages('report_l3.pdf')
    # input
    plt.plot(t, input_signal0)
    plt.xlabel('t')
    plt.ylabel('cos(x*3) + sin(x*2)')
    plt.title('input signal')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()


    fut_result = fut(input_signal0)

    ifut_result = ifut(fut_result)

    dut_result = dut(input_signal0)

    idut_result = idut(dut_result)

    dut_result_Walsh = dut_Walsh(input_signal0)

    idut_result_Walsh = idut_Walsh(dut_result_Walsh)


    plt.plot(t, fut_result)
    plt.xlabel('t')
    plt.ylabel('')
    plt.title('fut')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    plt.plot(t, dut_result)
    plt.xlabel('t')
    plt.ylabel('')
    plt.title('dut по Адамару')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    plt.plot(t, dut_result_Walsh)
    plt.xlabel('t')
    plt.ylabel('')
    plt.title('dut по Уолшу')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    plt.plot(t, ifut_result)
    plt.title('ifut')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    plt.plot(t, idut_result)
    plt.title('idut по Адамару')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    plt.plot(t, idut_result_Walsh)
    plt.title('idut по Уолшу')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()


    pp.close()


