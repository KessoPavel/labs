from cmath import *
import math
import time
import matplotlib.pyplot as plt
from matplotlib.backends.backend_pdf import PdfPages

def benchmark(func):
    def wrapper(*args, **kwargs):
        t = time.clock()
        res = func(*args, **kwargs)
        print("speed of execution ", func.__name__,  " : ", time.clock() - t)
        return res
    return wrapper


# fft and ifft
@benchmark
def fft(vector):
    def fan(vector):
        n = len(vector)
        result = [None] * n
        if n == 1:
            return vector

        even = fan(vector[0::2])
        odd = fan(vector[1::2])

        w_step = e ** (2 * pi * complex(0, -1) / n)
        w = 1

        for j in range(int(n / 2)):
            result[j] = even[j] + (w * odd[j])
            result[j + int(n / 2)] = even[j] - (w * odd[j])
            w *= w_step

        return result

    return list(fan(vector))


@benchmark
def ifft(vector):
    def fan(vector):
        n = len(vector)
        result = [None] * n
        if n == 1:
            return vector
        even = fan(vector[0::2])
        odd = fan(vector[1::2])
        w_step = e ** ((2 * pi * complex(0, 1)) / n)
        w = 1

        for j in range(int(n / 2)):
            result[j] = even[j] + w * odd[j]
            result[j + int(n / 2)] = even[j] - w * odd[j]
            w *= w_step

        return result

    result = fan(vector)
    return list(map(lambda x: x / N, result))


# ft & ift
@benchmark
def ft(vector):
    n = len(vector)
    result = [complex(0, 0)] * n

    for k in range(n):
        for m in range(n):
            w = e ** (2*pi*complex(0,-1) / n)
            result[k] += vector[m] * w**(k*m)

    return result


@benchmark
def ift(vector):
    n = len(vector)
    result = [complex(0, 0)] * n

    for k in range(n):
        for m in range(n):
            w = e ** (2 * pi * complex(0, -1) / n)
            result[k] += vector[m] * w**(-1*k*m)
        result[k] /= n

    return result


def svertca(c1, c2):
    c = [complex(0,0)] * len(c1)
    for i in range(len(c1)):
        c[i] = c1[i]*c2[i]
    return c


def corel(c1, c2):
    c = [complex(0, 0)] * len(c1)
    for i in range(len(c1)):
        c[i] = c1[i].conjugate() * c2[i]
    return c


if __name__ == '__main__':
    # initialization
    N = 8
    input_signal0 = [None] * N
    cos3x = [None] * N
    sin2x = [None] * N
    t = [0] * N
    step = 1 / pi

    for i in range(N):
        input_signal0[i] = math.cos(3*t[i]) + math.sin(2*t[i])
        cos3x[i] = math.cos(3*t[i])
        sin2x[i] = math.sin(2*t[i])
        if i < N - 1:
            t[i + 1] = t[i] + step

    pp = PdfPages('report.pdf')
    #input
    plt.plot(t, input_signal0)
    plt.xlabel('t')
    plt.ylabel('cos(x*3) + sin(x*2)')
    plt.title('input signal')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    #fft
    fft_result = fft(input_signal0)
    #ifft
    ifft_result = ifft(fft_result)
    #ft
    ft_result = ft(input_signal0)
    #ift
    ift_result = ift(ft_result)

    #amplitude fft
    pol = list(map(lambda x: polar(x), fft_result))
    a = list(map(lambda x: x[0], pol))
    plt.plot(t, a, 'o')
    plt.title('amplitude spectrum after fft')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    #amlitude ft
    pol_ft = list(map(lambda x: polar(x), ft_result))
    a_ft = list(map(lambda x: x[0], pol_ft))
    plt.plot(t, a_ft, 'o')
    plt.title('amplitude spectrum after ft')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    #phase fft
    p = list(map(lambda x: x[1], pol))
    plt.plot(t, p, 'o')
    plt.title('phase spectrum after fft')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    #phase ft
    p_ft = list(map(lambda x: x[1], pol_ft))
    plt.plot(t, p_ft, 'o')
    plt.title('phase spectrum after ft')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    #after ifft
    plt.plot(t, list(map(lambda x: x.real, ifft_result)))
    plt.xlabel('t')
    plt.ylabel('cos(x*3) + sin(x*2)')
    plt.title('signal after fft & ifft')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    #after ift
    plt.plot(t, list(map(lambda x: x.real, ifft_result)))
    plt.xlabel('t')
    plt.ylabel('cos(x*3) + sin(x*2)')
    plt.title('signal after ft & ift')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()


    #lab2
    plt.plot(t, cos3x)
    plt.xlabel('t')
    plt.ylabel('cos(x*3)')
    plt.title('cos(3x)')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    plt.plot(t, sin2x)
    plt.xlabel('t')
    plt.ylabel('sin(x*2)')
    plt.title('sin(2x)')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    c_cos = fft(cos3x)
    c_sin = fft(sin2x)

    sv = svertca(c_cos, c_sin)
    result_sv = ifft(sv)

    plt.plot(t, list(map(lambda x: x.real, result_sv)))
    plt.xlabel('t')
    plt.ylabel('svertka')
    plt.title('svertka')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    cor = corel(c_cos, c_sin)
    result_cor = ifft(cor)

    plt.plot(t, list(map(lambda x: x.real, result_cor)))
    plt.xlabel('t')
    plt.ylabel('cor')
    plt.title('cor')
    plt.grid(True)
    plt.axhline(0, color='blue')
    plt.axvline(0, color='blue')
    pp.savefig()
    plt.close()

    pp.close()
