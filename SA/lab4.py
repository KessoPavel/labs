from itertools import groupby

import nltk
from nltk.corpus import stopwords
import re
from collections import Counter
from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.backends.backend_pdf import PdfPages
import math
import random
from copy import copy

file_path = '/home/kesso/SA/lab4/'
medicine_file_name = ['medicine1', 'medicine2', 'medicine3', 'medicine4', 'medicine5',
                      'medicine6', 'medicine7', 'medicine8', 'medicine9', 'medicine10']
history_file_name = ['history1', 'history2', 'history3', 'history4', 'history5',
                     'history6', 'history7', 'history8', 'history9', 'history10']
education_file_name = ['education1', 'education2', 'education3', 'education4', 'education5',
                       'education6', 'education7', 'education8', 'education9', 'education10']


step = 0


def get_file_data(file_name):
    file = open(file_path + file_name, 'r')
    return file.read()


def get_words(texts):
    tokens = []
    for text in texts:
        tokens += nltk.word_tokenize(text.lower())

    return list(filter(lambda x: re.search("[а-яА-Я]", x.lower()), tokens))


def stemm_words_and_delete_stopwords(words):
    stemmer = nltk.SnowballStemmer("russian")
    stop = set(stopwords.words('russian'))

    a = list(filter(lambda x: x not in stop, words))

    return list(map(lambda x: stemmer.stem(x), a))


def get_vocabulary(words):
    a = Counter(words).most_common(60)

    print(a)

    return list(map(lambda x: x[0], a))


def distance(a, b):
    return math.sqrt(((b[0] - a[0])**2)  + ((b[1] - a[1])**2) + ((b[2] - a[2])**2))


def get_center_of_mass(points):
    np_array = []

    for p in points:
        np_array.append(np.array(p))

    vec = sum(np_array) / len(points)

    return list(vec)



def k_means(vectors, k):
    def fan(points, center_of_mass):
        #расчитать кластеры
        clusters = [[]] * len(center_of_mass)
        for i in range(len(center_of_mass)):
            clusters[i] = []

        for p in points:
            dist = list(map(lambda x: distance(x, p), center_of_mass))
            c_n = dist.index(min(dist))
            clusters[c_n].append(p)

        #расчитать новые центры масс
        new_center_of_mass = []
        for c in clusters:
            new_center_of_mass.append(get_center_of_mass(c))

        p =copy(clusters)
        p.append(center_of_mass)

        print_vector(p, ' ', False, True)

        #cравнить цм
        if center_of_mass == new_center_of_mass:
            clusters.append(new_center_of_mass)
            return clusters
        else:
            return fan(points, new_center_of_mass)

    c_o_m = random.sample(sum(vectors, []), k)

    p = copy(vectors)
    p.append(c_o_m)

    print_vector(p, 'Случайный выбор центров', False, False)

    return fan(sum(vectors, []), c_o_m)


def get_vector(text, vocabulary1, vocabulary2, vocabulary3):
    x = 0
    y = 0
    z = 0
    for word in vocabulary1:
        x += text.count(word)
    for word in vocabulary2:
        y += text.count(word)
    for word in vocabulary3:
        z += text.count(word)

    return x, y, z


def print_vector(vectors, t, p, k):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d', title=t)

    f = True

    for v in vectors[0]:
        if p and f:
            ax.scatter(v[0], v[1], v[2], c='r', marker='o', label='medicine')
            f = False
        else:
            ax.scatter(v[0], v[1], v[2], c='r', marker='o')

        if len(vectors) >= 4 and k:
            c = vectors[3][0]
            ax.plot([c[0], v[0]], [c[1], v[1]], [c[2], v[2]], c='r')

    f = True
    for v in vectors[1]:
        if p and f:
            ax.scatter(v[0], v[1], v[2], c='b', marker='o', label='history')
            f = False
        else:
            ax.scatter(v[0], v[1], v[2], c='b', marker='o')

        if len(vectors) >= 4 and k:
            c = vectors[3][1]
            ax.plot([c[0], v[0]], [c[1], v[1]], [c[2], v[2]], c='b')
        #ax.plot([0, v[0]], [0, v[1]], [0, v[2]], c='b')

    f = True
    for v in vectors[2]:
        if p and f:
            ax.scatter(v[0], v[1], v[2], c='g', marker='o', label='education')
            f = False
        else:
            ax.scatter(v[0], v[1], v[2], c='g', marker='o')

        if len(vectors) >= 4 and k:
            c = vectors[3][2]
            ax.plot([c[0], v[0]], [c[1], v[1]], [c[2], v[2]], c='g')
        #ax.plot([0, v[0]], [0, v[1]], [0, v[2]], c='g')

    col = ['r', 'b', 'g']
    i = 0
    if len(vectors) >= 4:
        for v in vectors[3]:
            ax.scatter(v[0], v[1], v[2], c=col[i], marker='^')
            #ax.plot([0, v[0]], [0, v[1]], [0, v[2]], c='k')
            i+=1

    if len(vectors) >= 5:
        v = vectors[4][0]
        dist = list(map(lambda x: distance(x, v), vectors[3]))
        c_n = dist.index(min(dist))

        c_c_o_m = vectors[3][c_n]

        ax.scatter(v[0], v[1], v[2], c='y', marker='o')
        ax.plot([c_c_o_m[0], v[0]], [c_c_o_m[1], v[1]], [c_c_o_m[2], v[2]], c='y')

    ax.legend()

    pp.savefig()
    plt.close()

    return


if __name__ == "__main__":
    medicine = []
    history = []
    education = []

    for file_name in medicine_file_name: medicine.append(get_file_data(file_name))
    for file_name in history_file_name: history.append(get_file_data(file_name))
    for file_name in education_file_name: education.append(get_file_data(file_name))

#vocabulary

    m_all_teaching_words = get_words(medicine[0:5])
    h_all_teaching_words = get_words(history[0:5])
    e_all_teaching_words = get_words(education[0:5])

    m_teaching_words = stemm_words_and_delete_stopwords(m_all_teaching_words)
    h_teaching_words = stemm_words_and_delete_stopwords(h_all_teaching_words)
    e_teaching_words = stemm_words_and_delete_stopwords(e_all_teaching_words)

    m_vocabulary = get_vocabulary(m_teaching_words)
    h_vocabulary = get_vocabulary(h_teaching_words)
    e_vocabulary = get_vocabulary(e_teaching_words)

#texts

    m_words = []
    h_words = []
    e_words = []

    for text in medicine[5:10]: m_words.append(get_words([text]))
    for text in history[5:10]: h_words.append(get_words([text]))
    for text in education[5:10]: e_words.append(get_words([text]))

    m_stemm_words = []
    h_stemm_words = []
    e_stemm_words = []

    for words in m_words: m_stemm_words.append(stemm_words_and_delete_stopwords(words))
    for words in h_words: h_stemm_words.append(stemm_words_and_delete_stopwords(words))
    for words in e_words: e_stemm_words.append(stemm_words_and_delete_stopwords(words))


#vectors
    m_vect = []
    h_vect = []
    e_vect = []

    all_stem_texts = m_stemm_words + h_stemm_words + e_stemm_words

    for text in m_stemm_words:
        m_vect.append(get_vector(text, m_vocabulary, h_vocabulary, e_vocabulary))

    for text in h_stemm_words:
            h_vect.append(get_vector(text, m_vocabulary, h_vocabulary, e_vocabulary))

    for text in e_stemm_words:
        e_vect.append(get_vector(text, m_vocabulary, h_vocabulary, e_vocabulary))

    print(m_vect)
    print(h_vect)
    print(e_vect)

    pp = PdfPages('report.pdf')
    print_vector([m_vect, h_vect, e_vect], 'before', True, False)

    result = k_means([m_vect, h_vect, e_vect], 3)

#

    m_words = []
    h_words = []
    e_words = []

    for text in medicine[0:3]: m_words.append(get_words([text]))
    for text in history[0:3]: h_words.append(get_words([text]))
    for text in education[0:3]: e_words.append(get_words([text]))

    m_stemm_words = []
    h_stemm_words = []
    e_stemm_words = []

    for words in m_words: m_stemm_words.append(stemm_words_and_delete_stopwords(words))
    for words in h_words: h_stemm_words.append(stemm_words_and_delete_stopwords(words))
    for words in e_words: e_stemm_words.append(stemm_words_and_delete_stopwords(words))

    m_vect = []
    h_vect = []
    e_vect = []

    for text in m_stemm_words:
        m_vect.append(get_vector(text, m_vocabulary, h_vocabulary, e_vocabulary))

    for text in h_stemm_words:
        h_vect.append(get_vector(text, m_vocabulary, h_vocabulary, e_vocabulary))

    for text in e_stemm_words:
        e_vect.append(get_vector(text, m_vocabulary, h_vocabulary, e_vocabulary))

    for v in m_vect:
        p = copy(result)
        p.append([v])
        print_vector(p, 'medicine', False, True)

    for v in h_vect:
        p = copy(result)
        p.append([v])
        print_vector(p, 'history', False, True)

    for v in e_vect:
        p = copy(result)
        p.append([v])
        print_vector(p, 'education', False, True)

    pp.close()




