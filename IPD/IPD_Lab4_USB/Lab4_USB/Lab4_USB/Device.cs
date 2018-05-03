using System.IO;
using UsbEject;

namespace Lab4
{
    class Device
    {
        private string name;
        private char letter;
        private long totalMemory; 
        private long freeMemory;
        private long busyMemory;
        private DirectoryInfo root;
        private bool extracted;

        public Device() { }

        public Device(string name, char letter, long total, long free, long busy, DirectoryInfo root)
        {
            this.name = name;
            this.letter = letter;
            this.totalMemory = total;
            this.freeMemory = free;
            this.busyMemory = busy;
            this.root = root;
        }

        public string Name
        {
            get { return name; }
            set { this.name = value; }
        }

        public char Letter
        {
            get { return letter; }
            set { this.letter = value; }
        }

        public long TotalMemory
        {
            get { return totalMemory; }
            set { this.totalMemory = value; }
        }

        public long FreeMemory
        {
            get { return freeMemory; }
            set { this.freeMemory = value; }
        }

        public long BusyMemory
        {
            get { return busyMemory; }
            set { this.busyMemory = value; }
        }

        public DirectoryInfo Root
        {
            get { return root; }
            set { this.root = value; }
        }

        public bool Extracted
        {
            get { return extracted; }
            set { this.extracted = value; }

        }

        public bool Eject()
        {
            if (!extracted)
                return false;

            VolumeDeviceClass volumeDeviceClass = new VolumeDeviceClass();
            foreach (Volume dev in volumeDeviceClass.Devices)
            {
                try
                {
                    if (dev.LogicalDrive.Equals(this.Letter + ":"))
                    {
                        dev.Eject(true);
                        volumeDeviceClass = new VolumeDeviceClass();
                        foreach (Volume temp in volumeDeviceClass)
                        {
                            if (temp.LogicalDrive.Equals(this.Letter + ":"))
                                return false;
                        }
                        return true;
                    }
                }
                catch
                {
                    break;
                }
            }
            return false;
        }

        public bool Equals(Device obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            return ((name == obj.name) & (letter == obj.letter) & (totalMemory == obj.totalMemory));
        }
    }
}
