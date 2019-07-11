def fun_1(a, b, c, d):
    return (a ** 2) * d - (a * b * c) - d


def fun_2(a, b, c, d):
    return (a * b * d) - (b ** 2) * c + c


def fun_3(a, b, c, d):
    return (a * c * d) - b * (c ** 2) + b


def fun_4(a, b, c, d):
    return a * (d ** 2) - b * c * d - a


def fun_5(a, b, c, d):
    return a * d - b * c


def function(a, b, c, d):
    if fun_1(a, b, c, d) != 0:
        return False

    if fun_2(a, b, c, d) != 0:
        return False

    if fun_3(a, b, c, d) != 0:
        return False

    if fun_4(a, b, c, d) != 0:
        return False

    if fun_5(a, b, c, d) == 0:
        return False

    return True


for a in range(-5, 5):
    for b in range(-5, 5):
        for c in range(-5, 5):
            for d in range(-5, 5):
                if function(a, b, c, d):
                    print("a/b/c/d:     " + str(a) + "/" + str(b) + "/" + str(c) + "/" + str(d))