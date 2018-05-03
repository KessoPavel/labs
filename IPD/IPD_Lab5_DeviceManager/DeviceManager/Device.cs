using System;
using System.Collections.Generic;

namespace lab5
{
    class Device
    {

        private string name;
        private string deviceID;
        private bool status;
        private string guid;
        private string manufactire;
        private string[] hardvareID;
        private List<SysFile> sysFiel;

        public string Name
        {
            get { return name;}
            set { name = value; }
        }
        public String DeviceID
        {
            get { return deviceID; }
            set { deviceID = value; }
        }
        public string GUID
        {
            get { return guid; }
            set { guid = value; }
        }
        public string[] HardwareID
        {
            get { return hardvareID; }
            set { hardvareID = value; }
        }
        public string Manufacturer
        {
            get { return manufactire; }
            set { manufactire = value; }
        }
        public List<SysFile> SysFiles
        {
            get { return sysFiel; }
            set { sysFiel = value; }
        }
        public bool Status
        {
            get { return status; }
            set { status = value; }
        }

        public Device(string name, string deviceID, string GUID, string[] hardwareID, string manufacturer,  
             List<SysFile> sysFiles, bool status)
        {
            this.Name = name;
            this.DeviceID = deviceID;
            this.GUID = GUID;
            this.HardwareID = hardwareID;
            this.Manufacturer = manufacturer;
            this.SysFiles = sysFiles;
            this.Status = status;
        }

    }
}
