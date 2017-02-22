import matplotlib.pyplot as plt

# threads = [1, 2, 4, 8, 16, 32, 64, 128, 256, 512,1024, 2048]
# executTime = [392.554666, 291.341612, 253.872977, 261.757952 , 276.949115, 430.78558,
# 687.6112631, 1598.569178, 3597.839866, 8841.31897,  13165.561138, 19072.098393]

# plt.title("Median Multi Thread")
# plt.xlabel("Threads")
# plt.ylabel("Execution Time in ms")
# plt.plot(executTime, 'ro')
# plt.plot(executTime)
# plt.xticks(range(len(threads)),threads,size="small")
# plt.show()


threads = [1, 2, 4, 8, 16, 32, 64, 128, 256, 512,1024, 2048]
executTime = [20.164797,23.3915, 24.543161, 28.511929 , 21.41598,  22.632331,
29.058057, 51.008348, 83.167774, 134.091528,   181.608817, 318.295081]

plt.title("Mean Multi Thread")
plt.xlabel("Threads")
plt.ylabel("Execution Time in ms")
plt.plot(executTime, 'ro')
plt.plot(executTime,'g')
plt.xticks(range(len(threads)),threads,size="small")
plt.show()
