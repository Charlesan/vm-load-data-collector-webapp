import threading
import time
import urllib2, commands

loadcollectorvmip = "10.1.1.240"

def timer_start():
    t = threading.Timer(5,test_func,())
    t.start()

def test_func():
	weightdata = urllib2.urlopen("http://" + loadcollectorvmip + ":8080/vm-load-data-collector-webapp/vm/weight/getVmWeight").read()
	vmweight = eval(weightdata)

	cmd = 'echo \"'
	for (vm, weight) in vmweight.items():
		cmd += "set weight cluster_vm/" + vm + " " + bytes(weight) + ";"
	cmd += '\" | socat stdio /var/run/haproxy.sock'
	print cmd

	(status, output) = commands.getstatusoutput(cmd)
	print output

	timer_start()

if __name__ == "__main__":
    timer_start()
    while True:
        time.sleep(1)