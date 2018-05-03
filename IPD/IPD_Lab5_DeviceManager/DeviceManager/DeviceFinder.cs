using System.Collections.Generic;
using System.Management;


namespace lab5
{
    class DeviceFinder
    {
        public List<Device> Devices { get; }

        public DeviceFinder()
        {
            Devices = new List<Device>();
        }

        public void FindDevices()
        {
            ManagementObjectSearcher deviceList = new ManagementObjectSearcher("Select * from Win32_PnPEntity");

            if (deviceList == null)
            {
                return;
            }

            foreach (ManagementObject device in deviceList.Get())
            {
                    List<SysFile> sysFiles = new List<SysFile>();

                    foreach (var sysFile in device.GetRelated("Win32_SystemDriver"))
                    {
                       sysFiles.Add(new SysFile(sysFile["PathName"]?.ToString(), sysFile["Description"]?.ToString()));
                    }

                    Devices.Add(new Device(
                        device["Name"]?.ToString(),
                        device["DeviceID"]?.ToString(),
                        device["ClassGuid"]?.ToString(),
                        device["HardwareID"] != null ? (string[])device["HardwareID"] : null,
                        device["Manufacturer"]?.ToString(),
                        sysFiles,
                        device.GetPropertyValue("Status").ToString().Equals("OK")));
            }

        }

        public bool TurnOnOff(Device device)
        {
            foreach (ManagementObject dev in new ManagementObjectSearcher("Select * from Win32_PnPEntity").Get())
            {
                if (dev["DeviceID"].ToString().Equals(device.DeviceID))
                {
                    dev.InvokeMethod( !device.Status ? "Enable" : "Disable", new object[] { true });

                    if(Сheck(device))
                    {
                        device.Status = !device.Status;
                        return true;
                    }

                    break;
                }
            }

            return false;
        }

        public bool Сheck(Device device)
        {
            foreach (ManagementObject dev in new ManagementObjectSearcher("Select * from Win32_PnPEntity").Get())
            {
                if (dev["DeviceID"].ToString().Equals(device.DeviceID))
                {
                    return dev["Status"].ToString().Equals("OK") == !device.Status;
                }
            }
            return false;
        }

    }
}
