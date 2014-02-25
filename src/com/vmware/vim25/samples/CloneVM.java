package com.vmware.vim25.samples;

import java.net.URL;

import com.vmware.vim25.VirtualMachineCloneSpec;
import com.vmware.vim25.VirtualMachineRelocateSpec;
import com.vmware.vim25.mo.Folder;
import com.vmware.vim25.mo.InventoryNavigator;
import com.vmware.vim25.mo.ServiceInstance;
import com.vmware.vim25.mo.Task;
import com.vmware.vim25.mo.VirtualMachine;

/**
 * http://vijava.sf.net
 * @author Steve Jin
 */

public class CloneVM 
{
  public static void main(String[] args) throws Exception
  {
    if(args.length!=5)
    {
      System.out.println("Usage: java CloneVM <url> " +
      "<username> <password> <vmname> <clonename>");
      System.exit(0);
    }

    String vmname = args[3];
    String cloneName = args[4];

    ServiceInstance si = new ServiceInstance(
        new URL(args[0]), args[1], args[2], true);

    Folder rootFolder = si.getRootFolder();
    VirtualMachine vm = (VirtualMachine) new InventoryNavigator(
        rootFolder).searchManagedEntity(
            "VirtualMachine", vmname);

    if(vm==null)
    {
      System.out.println("No VM " + vmname + " found");
      si.getServerConnection().logout();
      return;
    }

    VirtualMachineCloneSpec cloneSpec = 
      new VirtualMachineCloneSpec();
    cloneSpec.setLocation(new VirtualMachineRelocateSpec());
    cloneSpec.setPowerOn(false);
    cloneSpec.setTemplate(false);

    Task task = vm.cloneVM_Task((Folder) vm.getParent(), 
        cloneName, cloneSpec);
    System.out.println("Launching the VM clone task. " +
    		"Please wait ...");

    String status = task.waitForMe();
    if(status==Task.SUCCESS)
    {
      System.out.println("VM got cloned successfully.");
    }
    else
    {
      System.out.println("Failure -: VM cannot be cloned");
    }
  }
}
