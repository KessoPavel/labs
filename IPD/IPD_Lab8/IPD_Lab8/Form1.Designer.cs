namespace IPD_Lab8
{
    partial class Form1
    {
        /// <summary>
        /// Обязательная переменная конструктора.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Освободить все используемые ресурсы.
        /// </summary>
        /// <param name="disposing">истинно, если управляемый ресурс должен быть удален; иначе ложно.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Код, автоматически созданный конструктором форм Windows

        /// <summary>
        /// Требуемый метод для поддержки конструктора — не изменяйте 
        /// содержимое этого метода с помощью редактора кода.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.enable = new System.Windows.Forms.Button();
            this.workMode = new System.Windows.Forms.ComboBox();
            this.eMail = new System.Windows.Forms.TextBox();
            this.labelb = new System.Windows.Forms.Label();
            this.saveSettings = new System.Windows.Forms.Button();
            this.fileSize = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(13, 13);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(159, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Изначальный режим работы :";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(13, 46);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(131, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "e-Mail куда отправляем :";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(14, 82);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(158, 13);
            this.label3.TabIndex = 2;
            this.label3.Text = "Размер файла для отправки :";
            // 
            // enable
            // 
            this.enable.Location = new System.Drawing.Point(188, 116);
            this.enable.Name = "enable";
            this.enable.Size = new System.Drawing.Size(75, 23);
            this.enable.TabIndex = 3;
            this.enable.Text = "Включить";
            this.enable.UseVisualStyleBackColor = true;
            this.enable.Click += new System.EventHandler(this.Enable_Click);
            // 
            // workMode
            // 
            this.workMode.FormattingEnabled = true;
            this.workMode.Items.AddRange(new object[] {
            "программа включена",
            "программа выключена"});
            this.workMode.Location = new System.Drawing.Point(188, 10);
            this.workMode.Name = "workMode";
            this.workMode.Size = new System.Drawing.Size(190, 21);
            this.workMode.TabIndex = 4;
            // 
            // eMail
            // 
            this.eMail.Location = new System.Drawing.Point(188, 46);
            this.eMail.Name = "eMail";
            this.eMail.Size = new System.Drawing.Size(190, 20);
            this.eMail.TabIndex = 5;
            // 
            // labelb
            // 
            this.labelb.AutoSize = true;
            this.labelb.Location = new System.Drawing.Point(294, 85);
            this.labelb.Name = "labelb";
            this.labelb.Size = new System.Drawing.Size(14, 13);
            this.labelb.TabIndex = 7;
            this.labelb.Text = "B";
            // 
            // saveSettings
            // 
            this.saveSettings.Location = new System.Drawing.Point(97, 116);
            this.saveSettings.Name = "saveSettings";
            this.saveSettings.Size = new System.Drawing.Size(75, 23);
            this.saveSettings.TabIndex = 8;
            this.saveSettings.Text = "Сохранить настройки";
            this.saveSettings.UseVisualStyleBackColor = true;
            this.saveSettings.Click += new System.EventHandler(this.SaveSettings_Click);
            // 
            // fileSize
            // 
            this.fileSize.Location = new System.Drawing.Point(188, 82);
            this.fileSize.Name = "fileSize";
            this.fileSize.Size = new System.Drawing.Size(100, 20);
            this.fileSize.TabIndex = 9;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(390, 151);
            this.Controls.Add(this.fileSize);
            this.Controls.Add(this.saveSettings);
            this.Controls.Add(this.labelb);
            this.Controls.Add(this.eMail);
            this.Controls.Add(this.workMode);
            this.Controls.Add(this.enable);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button enable;
        private System.Windows.Forms.ComboBox workMode;
        private System.Windows.Forms.TextBox eMail;
        private System.Windows.Forms.Label labelb;
        private System.Windows.Forms.Button saveSettings;
        private System.Windows.Forms.TextBox fileSize;
    }
}

