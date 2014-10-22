# This shell script helps users install dependencies and compile our auxiliary programs
# Bill Xue <xueeinstein@gmail.com>

# get OS bit
ARCH=$(uname -m | sed 's/x86_//;s/i[3-7]86/32/')

function isinstalled {
	if [ $1 == "Debian" ]; then
		if dpkg-query -l $2 > /dev/null 2>&1; then
			return 0 
		else
			return 1 
		fi
	elif [ $1 == "Redhat" ]; then
		if yum list installed $2 > /dev/null 2>&1; then
			return 1
		else
			return 0
		fi
	fi
}

function installPack {
	if [ $1 == "Debian" ]; then
		if [ $USER == "root" ]; then
			apt-get install $2
		else
			echo "begin install $2"
			sudo apt-get install $2
		fi
	elif [ $1 == "Redhat" ]; then
		if [ $USER == "root" ]; then
			yum install $2
		else
			echo "begin install $2"
			sudo yum install $2
		fi
	fi
}
# get OS
if [ -f /etc/lsb_release ]; then
	. /etc/lsb_release
	OS=$DISTRIB_ID
	VER=$DISTRIB_RELEASE
elif [ -f /etc/debian_version ]; then
	# code for Debian or Ubuntu
	OS=Debian
	VER=$(cat /etc/debian_version)

	# get RNAfold
	if [ $ARCH == 32 ]; then
		wget -c http://www.tbi.univie.ac.at/RNA/packages/deb/vienna-rna_2.1.7-1_i386.deb
		chmod +x vienna-rna_2.1.7-1_i386.deb
	elif [ $ARCH == 64 ]; then
		wget -c http://www.tbi.univie.ac.at/RNA/packages/deb/vienna-rna_2.1.7-1_amd64.deb
		chmod +x vienna-rna_2.1.7-1_amd64.deb
	fi

	# install RNAfold globally
	if [ $USER == "root" ]; then
		dpkg -i vienna-rna_2* 
	else
		sudo dpkg -i vienna-rna_2*
	fi

elif [ -f /etc/redhat-release ]; then
	# code for Red Hat, CentOS and Fedora
	OS=Redhat
	wget -c http://www.tbi.univie.ac.at/RNA/packages/source/ViennaRNA-2.1.8.tar.gz
	tar -zxvf ViennaRNA-2.1.8.tar.gz
	cd ViViennaRNA-2.1.8
	./configure
	make
	if [ $USER == "root" ]; then
		make install
	else
		sudo make install
	fi
	cd ..
else
	OS=$(uname -s)
	VER=$(uname -r)
fi

# check installed 
if isinstalled $OS "apache2"; then
	echo "apache2 installed"
else
	installPack $OS "apache2"
fi

if isinstalled $OS "php5"; then
	echo "php5 installed"
else
	installPack $OS "php5 libapache2-mod-php5"
fi

if isinstalled $OS "mysql-server"; then
	echo "mysql installed"
else
	installPack $OS "mysql-server"
	echo "To configure mysql, please visit http://community.linuxmint.com/tutorial/view/486"
	echo "After manually config, please run this shell script again."
	exit 0
fi

if isinstalled $OS "python"; then
	echo "python installed"
	# check python version
	case "$(python --version 2>&1)" in
	*" 2."*)
    	echo "Compatible python version!"
        ;;
	*)
    	echo "Our python script don't support 3.x version!"
        exit 0
	    ;;
	esac
else
	installPack $OS "python2.7"
fi


if isinstalled $OS "imagemagick"; then
	echo "imagemagick installed"
else
	installPack $OS "imagemagick" 
fi

# install our auxiliary programs
if [ $ARCH == 64 ]; then
	wget -c http://dev.mysql.com/get/Downloads/Connector-C/mysql-connector-c-6.1.5-linux-glibc2.5-x86_64.tar.gz
elif [ $ARCH == 32 ]; then
	wget -c http://dev.mysql.com/get/Downloads/Connector-C/mysql-connector-c-6.1.5-linux-glibc2.5-i686.tar.gz
fi

tar -zxvf mysql-connector-c-6.1.5-linux-glibc2.5* 
mv mysql-connector-c-6.1.5-linux-glibc2.5* mysql-connector

# compile import
cd a
make
cd ../b
make install
