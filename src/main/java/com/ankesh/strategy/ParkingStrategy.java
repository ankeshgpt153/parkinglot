/**
 * 
 */
package com.ankesh.strategy;

/**
 * @author ankesh
 *
 */
public interface ParkingStrategy
{
	public void add(int i);
	
	public int getSlot();
	
	public void removeSlot(int slot);
}
