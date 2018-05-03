using System;
using System.Collections.Generic;
using System.IO;
using MediaDevices;
using WpdMtpLib;


namespace Lab4
{
    class USBManager
    {
        static public List<Device> GetDeviceList()
        {
            List<Device> answer = new List<Device>();
            foreach (var currentMediaDevice in MediaDevice.GetDevices())
            {
                Device device = new Device();
                try
                {
                    currentMediaDevice.Connect();
                }
                catch (System.IO.DirectoryNotFoundException ex)
                {
                    continue;
                }

                device.Name = currentMediaDevice.FriendlyName;

                if (currentMediaDevice.DeviceType == MediaDevices.DeviceType.Generic)
                {
                    DriveInfo[] allDrives = DriveInfo.GetDrives();
                    foreach (var currentDriver in allDrives)
                    {
                        if (currentDriver.DriveType == DriveType.Removable)
                        {
                            if (currentDriver.VolumeLabel == device.Name)
                            {
                                device.Letter = currentDriver.Name[0];
                                device.Root = currentDriver.RootDirectory;
                                device.TotalMemory = currentDriver.TotalSize;
                                device.FreeMemory = currentDriver.TotalFreeSpace;
                                device.BusyMemory = device.TotalMemory - device.FreeMemory;
                                device.Extracted = true;
                                break;
                            }
                        }
                    }
                }
                else
                {
                    device.Extracted = false;
                    device.Name += " - " + currentMediaDevice.Model;

                    MtpResponse res;
                    MtpCommand command = new MtpCommand();

                    string[] deviceIds = command.GetDeviceIds();
                    if (deviceIds.Length == 0) { continue; }

                    string targetDeviceId = String.Empty;
                    foreach (string deviceId in deviceIds)
                    {
                        if (currentMediaDevice.FriendlyName.Equals(command.GetDeviceFriendlyName(deviceId)))
                        {
                            targetDeviceId = deviceId;
                            break;
                        }
                    }
                    if (targetDeviceId.Length == 0) { continue; }
                    command.Open(targetDeviceId);

                    uint[] storageIds = null;


                    res = command.Execute(MtpOperationCode.GetStorageIDs, null, null);
                    if (res != null)
                    {
                        storageIds = Utils.GetUIntArray(res.Data);

                        if (storageIds.Length != 0)
                        {
                            try
                            {
                                res = command.Execute(MtpOperationCode.GetStorageInfo, new uint[1] { storageIds[0] }, null);
                                StorageInfo storageInfo = new StorageInfo(res.Data);

                                device.TotalMemory = (long)storageInfo.MaxCapacity;
                                device.FreeMemory = (long)storageInfo.FreeSpaceInBytes;
                                device.BusyMemory = device.TotalMemory - device.FreeMemory;
                            }
                            catch
                            {
                                device.TotalMemory = device.FreeMemory = device.BusyMemory = 0;
                            }
                        }
                    }

                }
                currentMediaDevice.Disconnect();
                answer.Add(device);
            }
            return answer;
        }
    }
}
