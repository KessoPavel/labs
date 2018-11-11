library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity D_Trigger is
    Port ( D : in STD_LOGIC;
           C : in STD_LOGIC;
           Q : out STD_LOGIC;
           nQ : out STD_LOGIC);
end D_Trigger;

architecture Behavioral of D_Trigger is    

	component JK_aRS_Trigger
		Port ( C : in STD_LOGIC;
			   J : in STD_LOGIC;
			   K : in STD_LOGIC;
			   notR : in STD_LOGIC;
			   notS : in STD_LOGIC;
			   Q : out STD_LOGIC;
			   notQ : out STD_LOGIC);
	end component;

	signal notD : STD_LOGIC;
	signal high : STD_LOGIC := '1';
    
begin
	jkt : JK_aRS_Trigger
	port map(
			C => C,
			J => D,
			K => notD,
			notR => high,
			notS => high,
			Q => Q,
			notQ => nQ);
	
	change_D: process(D)
	begin
		notD <= not D;
	end process;
	
end Behavioral;
