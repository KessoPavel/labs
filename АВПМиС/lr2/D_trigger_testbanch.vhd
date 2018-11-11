library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
LIBRARY work;

entity D_trigger_testbanch is
end D_trigger_testbanch;

architecture Behavioral of D_trigger_testbanch is    

component D_Trigger
	Port ( D : in STD_LOGIC;
           C : in STD_LOGIC;
           Q : out STD_LOGIC;
           nQ : out STD_LOGIC);
end component;

signal C : STD_LOGIC := '0';
signal D : STD_LOGIC := '0';
signal Q : STD_LOGIC;
signal nQ : STD_LOGIC;

constant clk_period : time := 10ns;

begin
uut : D_Trigger port map(
		D => D,
		C => C,
		Q => Q,
		nQ => nQ);

clk_process :process
  begin
	C <= '0';
  wait for clk_period/2;
	C <= '1';
  wait for clk_period/2;
end process;
  
stim_proc: process
  begin  
	D <= '0';
  wait for clk_period + clk_period/3;
	D <= '1';  
	wait for clk_period/2;
	D <= '0';
	wait for clk_period;
	D <= '1';
	wait for 2 * clk_period;
	D <= '0';
	wait for clk_period + clk_period/2;
	D <= '1';
	wait for 2 * clk_period;
	
  end process;  

end Behavioral;
