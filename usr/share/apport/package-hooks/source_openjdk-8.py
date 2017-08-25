'''Apport package hook for openjdk-8 packages.

Copyright (C) 2017 Canonical Ltd.
Author: Tiago Stürmer Daitx <tiago.daitx@canonical.com>'''

import os
import re
import sys
from apport.hookutils import *

def add_info(report, ui=None):
    attach_conffiles(report,'openjdk-8-jre-headless', ui=ui)

    if report['ProblemType'] == 'Crash' and 'ProcCwd' in report:
        # attach hs_err_<pid>.pid file
        cwd = report['ProcCwd']
        pid_line = re.search("Pid:\t(.*)\n", report["ProcStatus"])
        if pid_line:
            pid = pid_line.groups()[0]
            path = "%s/hs_err_pid%s.log" % (cwd, pid)
            # make sure if exists
            if os.path.exists(path):
                content = read_file(path)
                # only attach if smaller than 100 KB
                # see LP: #1696814
                if sys.getsizeof(content) < 100*1024:
                    report['HotspotError'] = content
                    report['Tags'] += ' openjdk-hs-err'
