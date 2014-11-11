#gerador de instancia para o orientWIS
#grafo de conflitos nao orientado -> saida do DGz(L)
"""formato

min link length
max link length
min z function value
max z function value
number of links
linkId x_sender y_sender x_receiver y_receiver z_function_value
...
linkId x_sender y_sender x_receiver y_receiver z_function_value


"""
import sys
import random
import math

argc = len(sys.argv)

if argc < 8:
    print "Too few arguments.\nSeven are required: filename nLinks minLength maxLength minZ maxZ escala"
    exit(0)

#argumentos nLinks minLength maxLength minZ maxZ escala
#os links se localizam no plano XY. com x e y no intervalo [0,escala)

n         = int(sys.argv[2])
minLength = float(sys.argv[3])
maxLength = float(sys.argv[4])
minZ      = float(sys.argv[5])
maxZ      = float(sys.argv[6])
escala    = float(sys.argv[7])

if maxZ < minZ:
    print "Inconsisten min and max Z values."
    exit(0)

if maxLength < minLength:
    print "Inconsisten min and max Length values."
    exit(0)


output = open(sys.argv[1],"w")

#min link length
output.write(str(minLength)+'\n')
#max link length
output.write(str(maxLength)+'\n')
#min interference radius
output.write(str(minZ)+'\n')
#max interference radius
output.write(str(maxZ)+'\n')
#numero de links
output.write(str(n)+'\n')
#linkid x_sender y_sender x_receiver y_receiver z_function_value
links = []
for i in range(n):
    x_sender = random.random() * escala
    y_sender = random.random() * escala

    length = random.random() * (maxLength - minLength)
    angle = random.random() * 2*math.pi

    x_receiver = math.cos(angle) * (minLength + length) + x_sender
    y_receiver = math.sin(angle) * (minLength + length) + y_sender

    z = minZ + (random.random() * (maxZ - minZ))

    output.write(("%.3f" % round(x_sender,3))+' ')
    output.write(("%.3f" % round(y_sender,3))+' ')
    output.write(("%.3f" % round(x_receiver,3))+' ')
    output.write(("%.3f" % round(x_receiver,3))+' ')
    output.write(("%.3f" % round(z,3))+'\n')

    link = (x_sender,y_sender,x_receiver,y_receiver,z*(minLength + length))
    links.append(link)
#
#arestas
for i in range(n):
    for j in range(i):
        if i != j:
            #distance between senders
            dx = links[i][0] - links[j][0]
            dy = links[i][1] - links[j][1]
            dist = math.sqrt(dx*dx + dy*dy)
            if dist < (links[i][4] + links[j][4]):
                #adiciona aresta
                output.write(str(i)+' '+str(j)+'\n')
output.close()
