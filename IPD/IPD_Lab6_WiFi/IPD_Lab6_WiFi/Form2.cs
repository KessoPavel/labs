using System.Collections.Generic;
using SimpleWifi;
using System.Diagnostics;
using System.Windows.Forms;
using System.Threading;

namespace IPD_Lab6_WiFi
{
    public partial class Form2 : Form
    {
        private string passworld;

        public Form2(string name)
        {
            InitializeComponent();
            label1.Text = name;
        }

        public void Method()
        {
            Thread.Sleep(1000000);
        }

        public string Passworld
        {
            get { this.Close(); return passworld; }
        }

        private void button1_Click(object sender, System.EventArgs e)
        {
            passworld = textBox1.Text;
            this.Visible = false;
        }

        private void button2_Click(object sender, System.EventArgs e)
        {
            passworld = null;
            this.Visible = false;
        }
    }
}
