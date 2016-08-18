#!/usr/bin/python3
# -*- coding: utf-8 -*-
# Author:Mine DOGAN <mine.dogan@agem.com.tr>

from base.plugin.abstract_plugin import AbstractPlugin


class Safe(AbstractPlugin):
    def __init__(self, context):
        super(Safe, self).__init__()
        self.context = context
        self.username = str(context.get_username())
        self.logger = self.get_logger()

        self.script = '/bin/bash ' + self.Ahenk.plugins_path() + 'usb/scripts/{0}'

        self.logger.debug('[USB - safe] Parameters were initialized.')


    def handle_safe_mode(self):
        self.execute(self.script.format('ENABLED_webcam.sh'), result=True)
        self.logger.debug('[USB - safe] Enabled webcam.')

        self.execute(self.script.format('ENABLED_printer.sh'), result=True)
        self.logger.debug('[USB - safe] Enabled printer.')

        self.execute(self.script.format('ENABLED_usbstorage.sh'), result=True)
        self.logger.debug('[USB - safe] Enabled usb storage.')

        self.execute(self.script.format('ENABLED_usbhid.sh'), result=True)
        self.logger.debug('[USB - safe] Enabled usb hid.')


def handle_mode(context):
    safe = Safe(context)
    safe.handle_safe_mode()
