import math
import random

file_name = '/home/kesso/WorkSpace/lab5/poker-hand-training-true.data.txt'


def get_data():

    data = []

    with open(file_name, 'r') as read_file:
        for line in read_file:
            my_str = line.strip('\n')

            data.append(my_str.split('\t'))

    return data


def get_learning_data(data):
    l_data = []
    for d in data:
        n_d = []

        n_d.append(float(d[1]))
        n_d.append(float(d[2]))
        n_d.append(float(d[3]))
        n_d.append(float(d[4]))
        n_d.append(float(d[5]))
        n_d.append(float(d[6]))
        n_d.append(float(d[7]))

        l_data.append(n_d)

    return l_data


def normilize_data(data):
    carat_min = min(list(map(lambda x: x[0], data)))
    carat_max = max(list(map(lambda x: x[0], data)))
    depth_min = min(list(map(lambda x: x[1], data)))
    depth_max = max(list(map(lambda x: x[1], data)))
    table_min = min(list(map(lambda x: x[2], data)))
    table_max = max(list(map(lambda x: x[2], data)))
    price_min = min(list(map(lambda x: x[3], data)))
    price_max = max(list(map(lambda x: x[3], data)))
    x_min = min(list(map(lambda x: x[4], data)))
    x_max = max(list(map(lambda x: x[4], data)))
    y_min = min(list(map(lambda x: x[5], data)))
    y_max = max(list(map(lambda x: x[5], data)))
    z_min = min(list(map(lambda x: x[6], data)))
    z_max = max(list(map(lambda x: x[6], data)))

    for d in data:
        d[0] = (d[0] - carat_min) / (carat_max - carat_min)
        d[1] = (d[1] - depth_min) / (depth_max - depth_min)
        d[2] = (d[2] - table_min) / (table_max - table_min)
        d[3] = (d[3] - price_min) / (price_max - price_min)
        d[4] = (d[4] - x_min) / (x_max - x_min)
        d[5] = (d[5] - y_min) / (y_max - y_min)
        d[6] = (d[6] - z_min) / (z_max - z_min)

    return data


class neuron:
    def __init__(self):
        self.incoming_synapses = []
        self.power = 0

    def get_weights(self):
        return list(map(lambda x: x.weight, self.incoming_synapses))


class synapse:
    def __init__(self, n, w):
        self.neuron = n
        self.weight = w


class input:
    def __init__(self, neur, i):
        self.outgoing_synapses = []

        for n in neur:
            self.outgoing_synapses.append(n.incoming_synapses[i])


def get_distance(v1, v2):
    d = 0
    for i in range(len(v1)):
        d = d + (v1[i] ** 2 - v2[i] ** 2)

    return d


class kohonenNetwork:
    learning_rate_factors = 0.7

    def __init__(self, inputs_count, outputs_count):
        self.neurons = []
        self.inputs = []

        for i in range(outputs_count):
            self.neurons.append(neuron())

        for n in self.neurons:
            for i in range(inputs_count):
                w = random.uniform(0.5 - (1 / math.sqrt(inputs_count)), 0.5 + (1 / math.sqrt(inputs_count)))
                n.incoming_synapses.append(synapse(n, w))

        for i in range(inputs_count):
            self.inputs.append(input(self.neurons, i))

    def print(self):
        print('neurons')
        for n in self.neurons:
            print(list(map(lambda x: x.weight, n.incoming_synapses)))

    #return (neuron, distance)
    def get_winner(self, vector):
        distances = []

        for n in self.neurons:
            distances.append(get_distance(vector, n.get_weights()))

        min_distance = distances.index(min(distances))

        return self.neurons[min_distance], min_distance

    def correction(self, n, vector):
        for i in range(len(vector)):
            n.incoming_synapses[i].weight = n.incoming_synapses[i].weight + self.learning_rate_factors * (vector[i] - n.incoming_synapses[i].weight)

    def learning(self, learning_set, epochs_num = 3):
        #-0.2 за эпоху
        clusters = []

        for epoch in range(epochs_num):
            clusters = []
            for i in range(len(self.neurons)):
                clusters.append([])

            for vector in learning_set:
                n, i = self.get_winner(vector)

                self.correction(n,vector)
                clusters[i].append(vector)

            self.learning_rate_factors = self.learning_rate_factors / 10

        return clusters

    def clas(self, vector):
        n, i = self.get_winner(vector)
        return i

if __name__ == '__main__':
    data = get_data()
    l_data = get_learning_data(data)
    n_data = normilize_data(l_data)

    k = kohonenNetwork(7, 6)

    clusters = k.learning(n_data, 1)

#Fair, Good, Very Good, Premium, Ideal


    for c in clusters:

        print('--------------------------')
        print(len(c))

        # fair = 0
        # good = 0
        # veryGood = 0
        # premium = 0
        # ideal = 0
        #
        # for i in c:
        #     ind = n_data.index(i)
        #
        #     quality = data[ind][0]
        #
        #
        #
        #     if quality == 'Fair':
        #         fair = fair + 1
        #     elif quality == 'Good':
        #         good = good + 1
        #     elif quality == 'VeryGood':
        #         veryGood = veryGood + 1
        #     elif quality == 'Premium':
        #         premium = premium + 1
        #     elif quality == 'Ideal':
        #         ideal = ideal + 1
        #
        # print('Fair : ', fair)
        # print('Good : ', good)
        # print('Very Good : ', veryGood)
        # print('Premium : ', premium)
        # print('Ideal : ', ideal)

    for i in range(100):
        print(data[i][0])
        print(k.clas(n_data[i]))



