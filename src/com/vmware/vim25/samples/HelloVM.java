package com.vmware.vim25.samples;

import java.net.URL;
import com.vmware.vim25.*;
import com.vmware.vim25.mo.*;

public class HelloVM 
{
	public static void main(String[] args) throws Exception
	{
		long start = System.currentTimeMillis();
		ServiceInstance si = new ServiceInstance(new URL("https://192.168.238.136/sdk"), "root", "1234567", true);
		long end = System.currentTimeMillis();
		System.out.println("time taken:" + (end-start));
		Folder rootFolder = si.getRootFolder();
		String name = rootFolder.getName();
		System.out.println("root:" + name);
		ManagedEntity[] mes = new InventoryNavigator(rootFolder).searchManagedEntities("VirtualMachine");
		if(mes==null || mes.length ==0)
		{
			return;
		}
		
		VirtualMachine vm = (VirtualMachine) mes[0]; 
		
		VirtualMachineConfigInfo vminfo = vm.getConfig();
		VirtualMachineCapability vmc = vm.getCapability();
		VirtualMachineStorageInfo vms = vm.getStorage();

		vm.getResourcePool();
		System.out.println("Hello " + vm.getName());
		System.out.println("GuestOS: " + vminfo.getGuestFullName());
		System.out.println("Multiple snapshot supported: " + vmc.isMultipleSnapshotsSupported());

		si.getServerConnection().logout();
	}

}