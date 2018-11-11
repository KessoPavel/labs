library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity RS_Trigger is
    Port ( R : in STD_LOGIC;
           S : in STD_LOGIC;
           C : in STD_LOGIC;
           a_notR : in STD_LOGIC;
           a_notS : in STD_LOGIC;
           Q : out STD_LOGIC;
           notQ : out STD_LOGIC);
end RS_Trigger;

architecture Behavioral of RS_Trigger is    
		
	component JK_aRS_Trigger
		Port ( C : in STD_LOGIC;
			   J : in STD_LOGIC;
			   K : in STD_LOGIC;
			   notR : in STD_LOGIC;
			   notS : in STD_LOGIC;
			   Q : out STD_LOGIC;
			   notQ : out STD_LOGIC);
	end component;
    
begin
	jkt : JK_aRS_Trigger
	port map(
			C => C,
			J => S,
			K => R,
			notR => a_notR,
			notS => a_notS,
			Q => Q,
			notQ => notQ);
end Behavioral;