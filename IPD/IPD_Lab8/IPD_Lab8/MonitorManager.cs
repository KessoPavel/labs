using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace IPD_Lab8
{
    class MonitorManager
    {
        private byte[] brightnessLevels;
        private int initialBrightness;

        public MonitorManager(IntPtr f)
        {
            //определим где искать
            System.Management.ManagementScope s = new System.Management.ManagementScope("root\\WMI");

            //определим запрос
            System.Management.SelectQuery q = new System.Management.SelectQuery("WmiMonitorBrightness");

            //найдем объекты
            System.Management.ManagementObjectSearcher managementObjectSearcher = new System.Management.ManagementObjectSearcher(s, q);

            //получим объекты
            System.Management.ManagementObjectCollection managementObjectCollection = managementObjectSearcher.Get();

            foreach (System.Management.ManagementObject o in managementObjectCollection)
            {
                brightnessLevels = (byte[])o.GetPropertyValue("Level");
                initialBrightness = (byte)o.GetPropertyValue("CurrentBrightness");
                break;
            }
            //освобождаем ресурсы
            managementObjectCollection.Dispose();
            managementObjectSearcher.Dispose();
        }

        public void SetInitialBrightness()
        {
            // определим где искать
            System.Management.ManagementScope s = new System.Management.ManagementScope("root\\WMI");

            // определим запрос
            System.Management.SelectQuery q = new System.Management.SelectQuery("WmiMonitorBrightnessMethods");

            // найдем объекты
            System.Management.ManagementObjectSearcher mos = new System.Management.ManagementObjectSearcher(s, q);
            // получим объекты
            System.Management.ManagementObjectCollection moc = mos.Get();
            //изменим яркость
            foreach (System.Management.ManagementObject o in moc)
            {
                o.InvokeMethod("WmiSetActive", new Object[] { false});
                break; //работает только для первого объекта
            }
            //освобождаем ресурсы
            moc.Dispose();
            mos.Dispose();
        }

        public void Update()
        {
            //определим где искать
            System.Management.ManagementScope s = new System.Management.ManagementScope("root\\WMI");

            //определим запрос
            System.Management.SelectQuery q = new System.Management.SelectQuery("WmiMonitorBrightness");

            //найдем объекты
            System.Management.ManagementObjectSearcher managementObjectSearcher = new System.Management.ManagementObjectSearcher(s, q);

            //получим объекты
            System.Management.ManagementObjectCollection managementObjectCollection = managementObjectSearcher.Get();

            foreach (System.Management.ManagementObject o in managementObjectCollection)
            {
                initialBrightness = (byte)o.GetPropertyValue("CurrentBrightness");
                break;
            }
            //освобождаем ресурсы
            managementObjectCollection.Dispose();
            managementObjectSearcher.Dispose();
        }
    }
}
