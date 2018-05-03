using System;
using System.Windows.Forms;

namespace lab5
{
    public partial class Form1 : Form
    {
        private DeviceFinder deviceFinder;
        private int selectedIndex;

        public Form1()
        {
            InitializeComponent();

            deviceFinder = new DeviceFinder();
            deviceFinder.FindDevices();
            PaintUI();
        }

        private void PaintUI()
        {
            listView1.Scrollable = true;

            listView1.View = View.Details;
            listView1.HeaderStyle = ColumnHeaderStyle.None;

            listView2.HeaderStyle = ColumnHeaderStyle.None;

            button1.Enabled = false;

            foreach (var device in deviceFinder.Devices)
            {
                addComponent(device);
            }
        }

        private void addComponent(Device device)
        {
            listView1.Items.Add(device.Name);
        }

        private void addDescription()
        {
            listView2.Clear();

            listView2.Items.Add("Name: " + deviceFinder.Devices[selectedIndex].Name);
            listView2.Items.Add("DeviceID: " + deviceFinder.Devices[selectedIndex].DeviceID);
            listView2.Items.Add("GUID: " + deviceFinder.Devices[selectedIndex].GUID);
            listView2.Items.Add("Manufacturer: " + deviceFinder.Devices[selectedIndex].Manufacturer);

            listView2.Items.Add("\nHardwareIDInfo: ");
            try
            {
                foreach (var hardwareID in deviceFinder.Devices[selectedIndex].HardwareID)
                {
                    listView2.Items.Add(hardwareID);
                }
            }
            catch (NullReferenceException) { }

            if (deviceFinder.Devices[selectedIndex].SysFiles.Count != 0)
            {
                listView2.Items.Add("Sys Files: ");
                foreach (var sysFile in deviceFinder.Devices[selectedIndex].SysFiles)
                {
                    listView2.Items.Add("Path: " + sysFile.Path);
                    listView2.Items.Add("Description: " + sysFile.Description);
                }
            }

            listView2.Items.Add("Status: " + (deviceFinder.Devices[selectedIndex].Status ? "on" : "off"));

            button1.Text = (deviceFinder.Devices[selectedIndex].Status ? "Turn off" : "Turn on");
            button1.Enabled = true;
        }

   

        private void listView1_MouseClick(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                var selected = listView1.FocusedItem;

                if (selected != null && selected.Bounds.Contains(e.Location) == true)
                {
                    selectedIndex = selected.Index;
                    addDescription();
                }

            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            MessageBox.Show(deviceFinder.TurnOnOff(deviceFinder.Devices[selectedIndex]) ? "Ok" : "Try again");            
            addDescription();
        }
    }
}
