#! /bin/bash
echo 'HDD:'
sudo hdparm -i /dev/sda | grep Model | cut -f 1-3 -d ',' --output-delimiter=$'\n' | cut -f 1-3 -d '=' --output-delimiter=': '           
sudo hdparm -i /dev/sda | grep 'Drive' | cut -f 3 -d ':' | cut -f 2-3 -d ' ' --output-delimiter=' ATA standarts supported: '
sudo hdparm -i /dev/sda | grep 'PIO modes' | cut -b 2-
sudo hdparm -i /dev/sda | grep ' DMA modes' -m 1 | cut -b 2-
echo 'Memory:'
df -a| awk '{used+=$3; free+=$4} END {print " Total: " used/1024/1024+free/1024/1024 "GB\n Used:  " used/1024/1024 "GB\n Free:  " free/1024/1024 "GB"}'
