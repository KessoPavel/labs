from PIL import Image
from pylab import *
import scipy.stats as st
import scipy.signal as sign
import numpy as np


# Transform RGB image to graysclae image
def img_transform(img_name):
    image = Image.open(img_name).convert('L')  # Открываем изображение и конвертируем в полутоновое
    image.save("gc_" + img_name)
    image.show()
    return image


# Image histogram and statistic
def img_histogram(image):
    img_array = np.array(image)
    hist_data = img_array.flatten()

    figure()
    hist(hist_data, 26)
    show()
    stat = [np.mean(hist_data), np.std(hist_data), st.mode(hist_data)[0][0], np.median(hist_data)]
    return hist_data, stat


# Image correlation
def img_correlation(img1, img2):
    x = np.array(img1)
    y = np.array(img2)

    return sign.correlate2d(x, y)


# Hypothesis check
def hyp_check(_dataset):
    chi2, p = st.chisquare(_dataset)
    print("Test Statistic: {}\np-value: {}".format(chi2, p))


if __name__ == "__main__":
    image1 = img_transform("img1.jpg")
    image2 = img_transform("img2.jpg")

    hist1, hist_stat1 = img_histogram(image1)
    hist2, hist_stat2 = img_histogram(image2)
    print('Average, rms, mode, median')
    print("First image: ", hist_stat1)
    print("Second image: ", hist_stat2)

    hist_correlation_coef = np.corrcoef(np.asarray(hist1).flatten(), np.asarray(hist2).flatten())[1, 0]
    print("Histogram correlation:", hist_correlation_coef)

    img_correl1 = img_correlation(image1, image2)
    print('Images` correlation\n', img_correl1)

    print('\nHypothesis:\n')
    hyp_check(np.array(hist1))
    hyp_check(np.array(hist2))