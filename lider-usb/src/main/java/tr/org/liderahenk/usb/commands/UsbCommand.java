package tr.org.liderahenk.usb.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.org.liderahenk.lider.core.api.log.IOperationLogService;
import tr.org.liderahenk.lider.core.api.persistence.IPluginDbService;
import tr.org.liderahenk.lider.core.api.rest.requests.ITaskRequest;
import tr.org.liderahenk.lider.core.api.service.ICommandContext;
import tr.org.liderahenk.lider.core.api.service.ICommandResult;
import tr.org.liderahenk.lider.core.api.service.ICommandResultFactory;
import tr.org.liderahenk.lider.core.api.service.enums.CommandResultStatus;

public class UsbCommand extends BaseCommand {

	private Logger logger = LoggerFactory.getLogger(UsbCommand.class);
	
	private ICommandResultFactory resultFactory;
	private IOperationLogService logService;
	private IPluginDbService pluginDbService;

	@Override
	public ICommandResult execute(ICommandContext context) {
		
		// TODO Modify parameter map before sending it to agent(s).
		ITaskRequest req = context.getRequest();
		Map<String, Object> parameterMap = req.getParameterMap();
		parameterMap.put("dummy-param", "dummy-param-value");
		
		logger.debug("Parameter map updated.");
		
		// TODO Modify entity objects related to plugin command via DB service
		Object entity = new Object();
		pluginDbService.save(entity);
		
		logger.debug("Entity saved successfully.");
		
		// TODO Modify result map to provide additional parameters or info before sending it back to console.
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dummy-param", "dummy-param-value");
		
		ICommandResult commandResult = resultFactory.create(CommandResultStatus.OK, new ArrayList<String>(), this, resultMap);

		// TODO logService
		
		return commandResult;
	}

	@Override
	public ICommandResult validate(ICommandContext context) {
		// TODO Validate before command execution
		return resultFactory.create(CommandResultStatus.OK, null, this, null);
	}

	@Override
	public String getCommandId() {
		// TODO Unique command ID used to match incoming REST requests to this Command class.
		return "RUN";
	}

	@Override
	public Boolean executeOnAgent() {
		// TODO True if we need to send a task to agent(s), false otherwise.
		return true;
	}

	public void setResultFactory(ICommandResultFactory resultFactory) {
		this.resultFactory = resultFactory;
	}

	public void setLogService(IOperationLogService logService) {
		this.logService = logService;
	}

	public void setPluginDbService(IPluginDbService pluginDbService) {
		this.pluginDbService = pluginDbService;
	}
	
}
