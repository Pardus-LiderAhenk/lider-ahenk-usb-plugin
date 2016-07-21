#!/usr/bin/python3
# -*- coding: utf-8 -*-
# Author:Mine DOGAN <mine.dogan@agem.com.tr>

import json

from base.plugin.abstract_plugin import AbstractPlugin

class Usb(AbstractPlugin):
    def __init__(self, data, context):
        super(Usb, self).__init__()
        self.data = data
        self.context = context
        self.logger = self.get_logger()
        self.message_code = self.get_message_code()

        self.parameters = json.loads(self.data)
        self.script = '/bin/bash ' + self.Ahenk.plugins_path() + 'usb/scripts/{0}'

        if self.has_attr_json(self.parameters, 'items') is True:
            self.items = self.parameters['items']

        self.command_vendor = "grep -l '{0}' /sys/bus/usb/devices/*/manufacturer | grep -o -P '.{{0,}}/.{{0,0}}'"
        self.command_model = "grep -l '{0}' {1}product"
        self.command_serial = "grep -l '{0}' {1}serial"
        self.command_authorized = "echo '{0}' > {1}authorized"

        self.command_serial_is_exist = 'if test -e {0}serial; then echo "exist"; else echo "not found"; fi'

        self.logger.debug('[USB] Parameters were initialized.')

    def handle_policy(self):
        try:
            self.logger.debug('[USB] Permissions will be applied for profile.')
            self.manage_permissions()

            if self.items:
                self.logger.debug('[USB] Blacklist/Whitelist will be created for profile.')
                self.create_blacklist_whitelist()

            self.logger.info('[USB] USB profile is handled successfully.')
            self.context.create_response(code=self.message_code.POLICY_PROCESSED.value,
                                         message='USB izinleri başarıyla güncellendi.')

        except Exception as e:
            self.logger.error('[USB] A problem occurred while handling USB policy. Error Message: {0}'.format(str(e)))
            self.context.create_response(code=self.message_code.POLICY_ERROR.value,
                                         message='USB politikası uygulanırken bir hata oluştu: {0}'.format(str(e)))

    def manage_permissions(self):

        self.logger.debug('[USB] Changing permissions...')

        if self.has_attr_json(self.parameters, 'webcam') is True:
            if self.parameters['webcam'] == '1':
                self.execute(self.script.format('ENABLED_webcam.sh'), result=True)
            elif self.parameters['webcam'] == '0':
                self.execute(self.script.format('DISABLED_webcam.sh'), result=True)

            self.logger.debug('[USB] Applied permission change for parameter "webcam"')
        else:
            self.logger.debug('[USB] Data has no parameter "webcam"')

        if self.has_attr_json(self.parameters, 'printer') is True:
            if self.parameters['printer'] == '1':
                self.execute(self.script.format('ENABLED_printer.sh'), result=True)
            elif self.parameters['printer'] == '0':
                self.execute(self.script.format('DISABLED_printer.sh'), result=True)

            self.logger.debug('[USB] Applied permission change for parameter "printer"')
        else:
            self.logger.debug('[USB] Data has no parameter "printer"')

        if self.has_attr_json(self.parameters, 'storage') is True:
            if self.parameters['storage'] == '1':
                self.execute(self.script.format('ENABLED_usbstorage.sh'), result=True)
            elif self.parameters['storage'] == '0':
                self.execute(self.script.format('DISABLED_usbstorage.sh'), result=True)

            self.logger.debug('[USB] Applied permission change for parameter "storage"')
        else:
            self.logger.debug('[USB] Data has no parameter "storage"')

        if self.has_attr_json(self.parameters, 'mouseKeyboard') is True:
            if self.parameters['mouseKeyboard'] == '1':
                self.execute(self.script.format('ENABLED_usbhid.sh'), result=True)
            elif self.parameters['mouseKeyboard'] == '0':
                self.execute(self.script.format('DISABLED_usbhid.sh'), result=True)

            self.logger.debug('[USB] Applied permission change for parameter "mouseKeyboard"')
        else:
            self.logger.debug('[USB] Data has no parameter "mouseKeyboard"')

        self.logger.debug('[USB] Permissions were applied.')


    def create_blacklist_whitelist(self):

        for item in self.items:
            item_parameters = json.loads(str(json.dumps(item)))

            vendor = item_parameters['vendor']
            model = item_parameters['model']
            serial_number = item_parameters['serialNumber']

            result_code, p_out, p_err = self.execute(self.command_vendor.format(vendor), result=True)
            folder_list = str(p_out).split('\n')
            folder_list.pop()

            if vendor == '':
                folder_list = []
                folder_list.append('/sys/bus/usb/devices/*/')

            for folder in folder_list:

                result_code, p_out, p_err = self.execute(self.command_model.format(model, folder), result=True)
                if p_out != '':
                    model_folder_list = str(p_out).split('\n')
                    model_folder_list.pop()

                    if vendor == '' and model == '':
                        model_folder_list = []
                        model_folder_list.append('/sys/bus/usb/devices/*/')

                    for model_folder in model_folder_list:
                        if 'product' in model_folder:
                            model_folder = model_folder.strip('product')

                        if model_folder != '/sys/bus/usb/devices/*/':
                            result_code, p_out, p_err = self.execute(self.command_serial_is_exist.format(model_folder), result=True)

                        if 'exist' in p_out or model_folder == '/sys/bus/usb/devices/*/':
                            result_code, p_out, p_err = self.execute(self.command_serial.format(serial_number, model_folder),
                                                                     result=True)
                            if p_out != '':
                                serial_folder_list = str(p_out).split('\n')
                                serial_folder_list.pop()

                                for serial_folder in serial_folder_list:
                                    serial_folder = serial_folder.strip('serial')
                                    if self.parameters['type'] == 'whitelist':
                                        self.execute(self.command_authorized.format('1', serial_folder), result=True)
                                        self.logger.debug(
                                            '[USB] Enabled the device. Vendor: {0}, Product: {1}, Serial Number: {2}'.format(
                                                vendor, model, serial_number))
                                    elif self.parameters['type'] == 'blacklist':
                                        self.execute(self.command_authorized.format('0', serial_folder), result=True)
                                        self.logger.debug(
                                            '[USB] Disabled the device. Vendor: {0}, Product: {1}, Serial Number: {2}'.format(
                                                vendor, model, serial_number))

                        elif 'not found' in p_out:
                            dir = ''
                            if model != '':
                                dir = model_folder
                            elif vendor != '':
                                dir = folder

                            if self.parameters['type'] == 'whitelist':
                                self.execute(self.command_authorized.format('1', dir), result=True)
                                self.logger.debug(
                                    '[USB] Enabled the device. Vendor: {0}, Product: {1}, Serial Number: {2}'.format(
                                        vendor, model, serial_number))
                            elif self.parameters['type'] == 'blacklist':
                                self.execute(self.command_authorized.format('0', dir), result=True)
                                self.logger.debug(
                                    '[USB] Disabled the device. Vendor: {0}, Product: {1}, Serial Number: {2}'.format(
                                        vendor, model, serial_number))

        self.logger.debug('[USB] Blacklist/Whitelist was created.')


def handle_policy(profile_data, context):
    manage = Usb(profile_data, context)
    manage.handle_policy()