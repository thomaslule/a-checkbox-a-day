## Author Thomas Bracher "thomas.bracher@cpe.fr"
require 'test/unit'
require 'selenium-webdriver'
require 'uri'

class SmokeTest < Test::Unit::TestCase
    def setup
        selenium_url = nil
        capabilities = nil
        if ENV['travis']
            selenium_url = 'http://' + ENV['SAUCE_USERNAME'] + ':' + ENV['SAUCE_ACCESS_KEY'] + '@ondemand.saucelabs.com:80/wd/hub'
            capabilities = Selenium::WebDriver::Remote::Capabilities.firefox
            capabilities['tunnel-identifier'] = ENV['TRAVIS_JOB_NUMBER']
            capabilities.version = '36.0'
            capabilities.platform = 'Windows 7'
            capabilities['name'] = 'Travis ' + ENV['TRAVIS_JOB_NUMBER']
            @appli_url = 'http://localhost:8080'
        elsif ENV['IN_DOCKER']
            selenium_url = 'http://selenium:4444/wd/hub'
            capabilities = :chrome
            @appli_url = 'http://172.17.42.1:' + (ENV['APPLI_URL'].split ':').last
        else
            selenium_url = 'http://localhost:4444/wd/hub'
            capabilities = :firefox
            @appli_url = 'http://localhost:8080'
        end

        @driver = Selenium::WebDriver.for(:remote, :url => selenium_url, :desired_capabilities => capabilities)
        @driver.manage.timeouts.implicit_wait = 5 # seconds
        @driver.get(@appli_url + '/clear')
    end

    def teardown
        @driver.close
    end

    def test_add_simple_task
        @driver.get(@appli_url + '/month')
        name = @driver.find_element(:name, 'name')
        name.send_keys('some task')
        name.submit

        last_task_name = @driver.find_element(:css, '.task-name')
        assert_equal(last_task_name.text, 'some task')
    end
end

puts 'run acceptance test'
