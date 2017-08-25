TARGETS = console-setup resolvconf mountkernfs.sh ufw screen-cleanup hostname.sh plymouth-log x11-common apparmor lm-sensors udev keyboard-setup networking hwclock.sh urandom mountdevsubfs.sh checkroot.sh checkfs.sh mountnfs.sh kmod mountall.sh checkroot-bootclean.sh bootmisc.sh procps mountall-bootclean.sh mountnfs-bootclean.sh
INTERACTIVE = console-setup udev keyboard-setup checkroot.sh checkfs.sh
udev: mountkernfs.sh
keyboard-setup: mountkernfs.sh udev
networking: resolvconf mountkernfs.sh urandom procps
hwclock.sh: mountdevsubfs.sh
urandom: hwclock.sh
mountdevsubfs.sh: mountkernfs.sh udev
checkroot.sh: hwclock.sh keyboard-setup mountdevsubfs.sh hostname.sh
checkfs.sh: checkroot.sh
mountnfs.sh: networking
kmod: checkroot.sh
mountall.sh: checkfs.sh checkroot-bootclean.sh
checkroot-bootclean.sh: checkroot.sh
bootmisc.sh: udev mountall-bootclean.sh mountnfs-bootclean.sh checkroot-bootclean.sh
procps: mountkernfs.sh udev
mountall-bootclean.sh: mountall.sh
mountnfs-bootclean.sh: mountnfs.sh
