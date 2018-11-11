library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
LIBRARY work;

entity RS_Trigger_TB is
end RS_Trigger_TB;

architecture Behavioral of RS_Trigger_TB is    

component RS_Trigger
	Port ( R : in STD_LOGIC;
           S : in STD_LOGIC;
           C : in STD_LOGIC;
           a_notR : in STD_LOGIC;
           a_notS : in STD_LOGIC;
           Q : out STD_LOGIC;
           notQ : out STD_LOGIC);
end component;

signal C : STD_LOGIC := '0';
signal R : STD_LOGIC := '0';
signal S : STD_LOGIC := '0';
signal a_notR : STD_LOGIC := '1';
signal a_notS : STD_LOGIC := '1';
signal Q : STD_LOGIC;
signal notQ : STD_LOGIC;

constant clk_period : time := 10ns;

begin
uut : RS_Trigger port map(
		C => C,
		S => S,
		R => R,
		a_notR => a_notR,
		a_notS => a_notS,
		Q => Q,
		notQ => notQ);

clk_process :process
  begin
	C <= '0';
  wait for clk_period/2;
	C <= '1';
  wait for clk_period/2;
end process;
  
stim_proc: process
  begin  
	S <= '0';
  wait for clk_period/3;
	S <= '1';  
  wait for clk_period + clk_period/2;	
  S <= '0';
  wait for clk_period + clk_period/2;
	R <= '1';
  wait for clk_period/2;
	R <= '0';  
	wait for clk_period;
	R <= '1';
  wait for clk_period/2;
	R <= '0';  
	wait for clk_period + clk_period/2;
	a_notS <= '0';
	wait for clk_period/2;
	a_notS <= '1';
	wait for clk_period;
	a_notR <= '0';
	wait for clk_period/2;
	a_notR <= '1';
	wait for clk_period;
  end process;  

end Behavioral;
