library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

LIBRARY work;

entity ShiftRegister is
    Port ( nG : in STD_LOGIC;
		   nSRCLR : in STD_LOGIC;
           nSRCKEN : in STD_LOGIC;
           SRCK : in STD_LOGIC;
           nSRLOAD : in STD_LOGIC;
           DS : in STD_LOGIC;
		   SER0 : in STD_LOGIC;
           SER1 : in STD_LOGIC;
           RCK : in STD_LOGIC;
           
           QH_out : out STD_LOGIC;
           
           QA : inout STD_LOGIC;
           QB : inout STD_LOGIC;
           QC : inout STD_LOGIC;
           QD : inout STD_LOGIC;
           QE : inout STD_LOGIC;
           QF : inout STD_LOGIC;
           QG : inout STD_LOGIC;
           QH : inout STD_LOGIC);
end ShiftRegister;

architecture Behavioral of ShiftRegister is    

component D_Trigger
	PORT(D : in STD_LOGIC;
		 C : in STD_LOGIC;
		 Q : out STD_LOGIC;
		 nQ : out STD_LOGIC
	);
end component;    

component D_Trigger_aRS
    Port ( D : in STD_LOGIC;
           C : in STD_LOGIC;
           notR : in STD_LOGIC;
           notS : in STD_LOGIC;
           Q : out STD_LOGIC;
           notQ : out STD_LOGIC);
end component;
    
component RS_Trigger
    Port ( R : in STD_LOGIC;
           S : in STD_LOGIC;
           C : in STD_LOGIC;
           a_notR : in STD_LOGIC;
           a_notS : in STD_LOGIC;
           Q : out STD_LOGIC;
           notQ : out STD_LOGIC);
end component;    
    
signal g : STD_LOGIC;          
    
signal a1 : STD_LOGIC;
signal a2 : STD_LOGIC;
signal a3 : STD_LOGIC;
signal a4 : STD_LOGIC;
signal a5 : STD_LOGIC;

signal b1 : STD_LOGIC;    
signal b2 : STD_LOGIC;   

signal d1 : STD_LOGIC;      
signal d2 : STD_LOGIC;      
signal d3 : STD_LOGIC;      
signal d4 : STD_LOGIC;      
signal d5 : STD_LOGIC; 

signal c1 : STD_LOGIC;      
     
signal dtq1 : STD_LOGIC;      
signal dtq2 : STD_LOGIC;      
signal dtq3 : STD_LOGIC;      
signal dtq4 : STD_LOGIC;      
signal dtq5 : STD_LOGIC; 
signal dtq6 : STD_LOGIC;      
signal dtq7 : STD_LOGIC;      
signal dtq8 : STD_LOGIC;

signal dtnq1 : STD_LOGIC;      
signal dtnq2 : STD_LOGIC;      
signal dtnq3 : STD_LOGIC;      
signal dtnq4 : STD_LOGIC;      
signal dtnq5 : STD_LOGIC; 
signal dtnq6 : STD_LOGIC;      
signal dtnq7 : STD_LOGIC;      
signal dtnq8 : STD_LOGIC;

signal dta1 : STD_LOGIC;      
signal dta2 : STD_LOGIC;      
signal dta3 : STD_LOGIC;      
signal dta4 : STD_LOGIC;      
signal dta5 : STD_LOGIC; 
signal dta6 : STD_LOGIC;      
signal dta7 : STD_LOGIC;      
signal dta8 : STD_LOGIC;

signal dtb1 : STD_LOGIC;      
signal dtb2 : STD_LOGIC;      
signal dtb3 : STD_LOGIC;      
signal dtb4 : STD_LOGIC;      
signal dtb5 : STD_LOGIC; 
signal dtb6 : STD_LOGIC;      
signal dtb7 : STD_LOGIC;      
signal dtb8 : STD_LOGIC;
    
signal dtc1 : STD_LOGIC;      
signal dtc2 : STD_LOGIC;      
signal dtc3 : STD_LOGIC;      
signal dtc4 : STD_LOGIC;      
signal dtc5 : STD_LOGIC; 
signal dtc6 : STD_LOGIC;      
signal dtc7 : STD_LOGIC;      
signal dtc8 : STD_LOGIC;
          
signal drsnq : STD_LOGIC;      
signal drsq : STD_LOGIC;

signal rsnq1 : STD_LOGIC;      
signal rsnq2 : STD_LOGIC;      
signal rsnq3 : STD_LOGIC;      
signal rsnq4 : STD_LOGIC;      
signal rsnq5 : STD_LOGIC; 
signal rsnq6 : STD_LOGIC;      
signal rsnq7 : STD_LOGIC;      

signal rsq1 : STD_LOGIC;      
signal rsq2 : STD_LOGIC;      
signal rsq3 : STD_LOGIC;      
signal rsq4 : STD_LOGIC;      
signal rsq5 : STD_LOGIC; 
signal rsq6 : STD_LOGIC; 

signal test : STD_LOGIC;
    
begin

dt1 : D_Trigger
port map(
		D => QA,
		C => c1,
		Q => dtq1,
		nQ => dtnq1);
		
dt2 : D_Trigger
port map(
		D => QB,
		C => c1,
		Q => dtq2,
		nQ => dtnq2);    

dt3 : D_Trigger
port map(
		D => QC,
		C => c1,
		Q => dtq3,
		nQ => dtnq3);
		
dt4 : D_Trigger
port map(
		D => QD,
		C => c1,
		Q => dtq4,
		nQ => dtnq4);
		
dt5 : D_Trigger
port map(
		D => QE,
		C => c1,
		Q => dtq5,
		nQ => dtnq5);
		
dt6 : D_Trigger
port map(
		D => QF,
		C => c1,
		Q => dtq6,
		nQ => dtnq6);
		
dt7 : D_Trigger
port map(
		D => QG,
		C => c1,
		Q => dtq7,
		nQ => dtnq7);
		
dt8 : D_Trigger
port map(
		D => QH,
		C => c1,
		Q => dtq8,
		nQ => dtnq8);
    
drs : D_Trigger_aRS
port map(
		D => d5,
		C => a4,
		notR => dtc1,
		notS => dta1,
		Q => drsq,
		notQ => drsnq);
    
rs1 : RS_Trigger
port map(
		R => drsnq,
		S => drsq,
		C => a4,
		a_notR => dtc2,
		a_notS => dta2,
		Q => rsq1,
		notQ => rsnq1);    

rs2 : RS_Trigger
port map(
		R => rsnq1,
		S => rsq1,
		C => a4,
		a_notR => dtc3,
		a_notS => dta3,
		Q => rsq2,
		notQ => rsnq2);    
				
rs3 : RS_Trigger
port map(
		R => rsnq2,
		S => rsq2,
		C => a4,
		a_notR => dtc4,
		a_notS => dta4,
		Q => rsq3,
		notQ => rsnq3);

rs4 : RS_Trigger
port map(
		R => rsnq3,
		S => rsq3,
		C => a4,
		a_notR => dtc5,
		a_notS => dta5,
		Q => rsq4,
		notQ => rsnq4);		
		    
rs5 : RS_Trigger
port map(
		R => rsnq4,
		S => rsq4,
		C => a4,
		a_notR => dtc6,
		a_notS => dta6,
		Q => rsq5,
		notQ => rsnq5);		

rs6 : RS_Trigger
port map(
		R => rsnq5,
		S => rsq5,
		C => a4,
		a_notR => dtc7,
		a_notS => dta7,
		Q => rsq6,
		notQ => rsnq6);
		
rs7 : RS_Trigger
port map(
		R => rsnq6,
		S => rsq6,
		C => a4,
		a_notR => dtc8,
		a_notS => dta8,
		notQ => rsnq7);		
		
g <= not nG;

a1 <= not ((not nSRCKEN) or (not a4));
a2 <= SRCK;
a3 <= (not a1) and (not a2);
a4 <= not (a3 and SRCK);

b1 <= not nSRLOAD;
b2 <= nSRCLR;

d1 <= not DS;
d2 <= not d1;
d3 <= d1 and SER0;
d4 <= d2 and SER1;
d5 <= d3 or d4;

c1 <= not RCK;

process (g, drsnq, rsnq1, rsnq2, rsnq3, rsnq4, rsnq5, rsnq6, rsnq7)
  begin
case g is
when '0' => QA <= 'Z';
  QB <= 'Z';
  QC <= 'Z';
  QD <= 'Z';
  QE <= 'Z';
  QF <= 'Z';
  QG <= 'Z';
  QH <= 'Z';
when '1' => QA <= not drsnq;
  QB <= not rsnq1;
  QC <= not rsnq2;
  QD <= not rsnq3;
  QE <= not rsnq4;
  QF <= not rsnq5;
  QG <= not rsnq6;
  QH <= not rsnq7;
when others =>  QA <= 'Z';
  QB <= 'X';
  QC <= 'X';
  QD <= 'X';
  QE <= 'X';
  QF <= 'X';
  QG <= 'X';
  QH <= 'X';
end case;
end process;

dta1 <= not (dtq1 and b1);
dtb1 <= not (dtnq1 and b1);
dtc1 <= not ((not dtb1) or (not b2));

dta2 <= not (dtq2 and b1);
dtb2 <= not (dtnq2 and b1);
dtc2 <= not ((not dtb2) or (not b2));

dta3 <= not (dtq3 and b1);
dtb3 <= not (dtnq3 and b1);
dtc3 <= not ((not dtb3) or (not b2));

dta4 <= not (dtq4 and b1);
dtb4 <= not (dtnq4 and b1);
dtc4 <= not ((not dtb4) or (not b2));

dta5 <= not (dtq5 and b1);
dtb5 <= not (dtnq5 and b1);
dtc5 <= not ((not dtb5) or (not b2));

dta6 <= not (dtq6 and b1);
dtb6 <= not (dtnq6 and b1);
dtc6 <= not ((not dtb6) or (not b2));

dta7 <= not (dtq7 and b1);
dtb7 <= not (dtnq7 and b1);
dtc7 <= not ((not dtb7) or (not b2));

dta8 <= not (dtq8 and b1);
dtb8 <= not (dtnq8 and b1);
dtc8 <= not ((not dtb8) or (not b2));

QH_out <= not rsnq7;

end Behavioral;