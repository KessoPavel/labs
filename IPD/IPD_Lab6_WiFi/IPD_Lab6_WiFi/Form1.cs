using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Forms;

namespace IPD_Lab6_WiFi
{
    public partial class Form1 : Form
    {
        private Refresher refresher;
        private List<Connection> connections;
        private Form2 form2;
        private Connection connection;

        public Form1()
        {
           
            refresher = new Refresher();
            InitializeComponent();
            InitilizeInformation();
            ShowTable();

           
            refresher.AddListerenToTimer(Refresh);
            refresher.Start();
        }

        private void InitilizeInformation()
        {
            SSID.Text = "";
            Mac.Text = "";
            Signal.Text = "";
            Type.Text = "";
        }

        private void SetInformation(Connection connection)
        {
            contextMenuStrip1.Items[0].Text = connection.IsConnected ? "Disconect" : "Conect";

            SSID.Text = connection.SSID;
            Mac.Text = connection.Mac;
            Signal.Text = connection.SignalStrength.ToString();
            Type.Text = connection.AuthType;
        }

        private void Refresh(object sender, EventArgs e)
        {
            ShowTable();
        }

        private void ShowTable()
        {
            if (!InvokeRequired)           {
                List<Connection> list = ConnectionManager.GetConnectionList();
                if(Equals(connections, list))
                {
                    return;
                }

                listView1.Clear();

                connections = list;
                foreach (var connection in list)
                {
                    listView1.Items.Add(connection.SSID);
                }
            }
            else
            {
                Invoke(new Action(ShowTable));
            }
        }


        private void connectToolStripMenuItem_Click(object sender, EventArgs e)
        {
            

            if (connection.IsConnected)
            {
                Connection.Disconect();
            }
            else
            {
                form2 = new Form2("Name : " + connection.SSID);
                form2.Show();
                form2.VisibleChanged += PassworldEntering;
                this.Enabled = false;
            }
        }

        private void Form2_VisibleChanged(object sender, EventArgs e)
        {
            throw new NotImplementedException();
        }

        private void PassworldEntering(object sender, EventArgs e)
        {
            string password = form2.Passworld;
            form2 = null;

            connection.Connect(password, CheckConnectionSuccess);

            this.Enabled = true;
        }

        private void CheckConnectionSuccess(bool success)
        {
            MessageBox.Show(success ? "Connect" : "Try again");
        }

        private void contextMenuStrip1_Opening(object sender, CancelEventArgs e)
        {

        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            try
            {
                connection = connections[listView1.SelectedIndices[0]];
                SetInformation(connection);
            }
            catch
            {
                ShowTable();
            }
        }
    }
}
