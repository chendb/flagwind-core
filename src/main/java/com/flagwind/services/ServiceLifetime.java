package com.flagwind.services;

/**
 * @author hbche
 */
public interface ServiceLifetime{
	boolean isAlive(ServiceEntry entry);
}